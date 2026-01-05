package com.wddyxd.orderservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.orderservice.mapper.FinancialFlowMapper;
import com.wddyxd.orderservice.mapper.OrderMainMapper;
import com.wddyxd.orderservice.pojo.DTO.FinancialFlowDTO;
import com.wddyxd.orderservice.pojo.entity.FinancialFlow;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.service.Interface.IFinancialFlowService;
import com.wddyxd.orderservice.stateMachine.StateMachineTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:32
 **/
@Service
public class IFinancialFlowServiceImpl extends ServiceImpl<FinancialFlowMapper, FinancialFlow> implements IFinancialFlowService {

    private static final Logger log = LoggerFactory.getLogger(IFinancialFlowServiceImpl.class);

    @Autowired
    private OrderMainMapper orderMainMapper;

    //如果是订单支付和订单退款财务的话允许一个orderId在某个时间只能存在一个正在支付财务,多个支付失败财务和一个支付成功财务
    @Override
    @Transactional
    public FinancialFlow OrderPaying(OrderMain orderMain) {
        FinancialFlow financialFlow = new FinancialFlow();
        financialFlow.setId(IdWorker.getId());
        financialFlow.setBuyerId(orderMain.getBuyerId());
        financialFlow.setMerchantId(orderMain.getMerchantId());
        financialFlow.setOrderId(orderMain.getId());
        financialFlow.setTradeType(2);
        financialFlow.setMoney(orderMain.getPayPrice());
        financialFlow.setStatus(0);
        financialFlow.setPayMethod(orderMain.getPayMethod());
        financialFlow.setRemark("正在进行订单支付");
        financialFlow.setOutTradeNo(orderMain.getId());
        // 加锁查询
        int activeCount = baseMapper.countActivePayment(financialFlow.getOrderId());
        if (activeCount > 0) {
            log.error("该订单已有未完成或已成功的支付记录，无法重复发起付款");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        baseMapper.insert(financialFlow);
        return financialFlow;
    }

    @Override
    @Transactional
    public void OrderPaid(OrderMain orderMain,FinancialFlow getFinancialFlow) {
        if(getFinancialFlow==null){
            log.error("错误格式的财务...");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        log.info("正在生成支付订单的最终财务");
        FinancialFlow financialFlow = baseMapper.selectOne(new LambdaQueryWrapper<FinancialFlow>()
                .eq(FinancialFlow::getOrderId,orderMain.getId())
                .eq(FinancialFlow::getTradeType,2)
                .eq(FinancialFlow::getStatus,0)
        );
        if(financialFlow== null||financialFlow.getIsDeleted()){
            log.error("未找到该订单的支付信息");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        financialFlow.setStatus(1);
        financialFlow.setRemark("完成订单支付");
        financialFlow.setTradeNo(getFinancialFlow.getTradeNo());
        financialFlow.setTransInId(getFinancialFlow.getTransInId());
        financialFlow.setTransOutId(getFinancialFlow.getTransOutId());
        baseMapper.updateById(financialFlow);
        orderMain.setPayMethod(2);
        orderMainMapper.updateById(orderMain);
        log.info("生成最终财务成功");
    }

    @Override
    public void OrderPayingFail(Long orderId) {
        log.info("正在生成取消订单的最终财务");
        FinancialFlow financialFlow = baseMapper.selectOne(new LambdaQueryWrapper<FinancialFlow>()
                .eq(FinancialFlow::getOrderId,orderId)
                .eq(FinancialFlow::getTradeType,2)
                .eq(FinancialFlow::getStatus,0)
        );
        if(financialFlow== null||financialFlow.getIsDeleted()){
            log.error("未找到该订单的支付信息");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        financialFlow.setStatus(2);
        baseMapper.updateById(financialFlow);
        log.info("生成取消订单的最终财务成功");
    }

    @Override
    @Transactional
    public FinancialFlow OrderRefunding(FinancialFlow oldFinancialFlow) {
        FinancialFlow financialFlow = new FinancialFlow();
        BeanUtil.copyProperties(oldFinancialFlow,financialFlow);
        financialFlow.setId(IdWorker.getId());
        financialFlow.setTradeType(3);
        financialFlow.setStatus(0);
        financialFlow.setRemark("正在进行订单退款");
        //每次发起新的退款请求时，生成新的退款单号
        financialFlow.setRefundId(financialFlow.getId()+IdWorker.getIdStr());
        // 加锁查询
        int activeCount = baseMapper.countActiveRefund(financialFlow.getOrderId());
        if (activeCount > 0) {
            log.error("该订单已有未完成或已成功的退款记录，无法重复发起退款");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        baseMapper.insert(financialFlow);
        return financialFlow;
    }

    @Override
    public void OrderRefunded(Long orderId) {
        FinancialFlow financialFlow = baseMapper.selectOne(new LambdaQueryWrapper<FinancialFlow>()
                .eq(FinancialFlow::getOrderId,orderId)
                .eq(FinancialFlow::getTradeType,3)
                .eq(FinancialFlow::getStatus,0)
        );
        if(financialFlow== null||financialFlow.getIsDeleted()){
            log.error("未找到该订单的退款信息");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        financialFlow.setStatus(1);
        baseMapper.updateById(financialFlow);
    }

    @Override
    public void OrderRefundingFail(Long orderId) {
        FinancialFlow financialFlow = baseMapper.selectOne(new LambdaQueryWrapper<FinancialFlow>()
                .eq(FinancialFlow::getOrderId,orderId)
                .eq(FinancialFlow::getTradeType,3)
                .eq(FinancialFlow::getStatus,0)
        );
        if(financialFlow== null||financialFlow.getIsDeleted()){
            log.error("未找到该订单的退款信息");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        financialFlow.setStatus(2);
        baseMapper.updateById(financialFlow);
    }

    @Override
    public void List(SearchDTO searchDTO) {

    }



}
