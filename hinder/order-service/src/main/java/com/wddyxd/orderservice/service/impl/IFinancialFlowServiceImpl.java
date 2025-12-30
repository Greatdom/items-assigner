package com.wddyxd.orderservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.orderservice.mapper.FinancialFlowMapper;
import com.wddyxd.orderservice.pojo.DTO.FinancialFlowDTO;
import com.wddyxd.orderservice.pojo.entity.FinancialFlow;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.service.Interface.IFinancialFlowService;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:32
 **/
@Service
public class IFinancialFlowServiceImpl extends ServiceImpl<FinancialFlowMapper, FinancialFlow> implements IFinancialFlowService {
    @Override
    public void add(FinancialFlowDTO financialFlowDTO) {

    }

    @Override
    public FinancialFlow paying(OrderMain orderMain) {
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
        if(financialFlow.getTradeType()==2){
            financialFlow.setOutTradeNo(orderMain.getId());
        }else{
            financialFlow.setOutTradeNo(financialFlow.getId());
        }
        baseMapper.insert(financialFlow);
        return financialFlow;
    }

    @Override
    public FinancialFlow refunding(FinancialFlow oldFinancialFlow) {
        FinancialFlow financialFlow = new FinancialFlow();
        BeanUtil.copyProperties(oldFinancialFlow,financialFlow);
        financialFlow.setId(IdWorker.getId());
        financialFlow.setTradeType(3);
        financialFlow.setStatus(0);
        financialFlow.setRemark("正在进行订单退款");
        baseMapper.insert(financialFlow);
        return financialFlow;
    }

    @Override
    public void List(SearchDTO searchDTO) {

    }
}
