package com.wddyxd.orderservice.service.impl;


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
    public void paying(OrderMain orderMain) {
        FinancialFlow financialFlow = new FinancialFlow();
        financialFlow.setBuyerId(orderMain.getBuyerId());
        financialFlow.setMerchantId(orderMain.getMerchantId());
        financialFlow.setOrderId(orderMain.getId());
        financialFlow.setTradeType(2);
        financialFlow.setMoney(orderMain.getPayPrice());
        financialFlow.setStatus(0);
        financialFlow.setPayMethod(orderMain.getPayMethod());
        financialFlow.setRemark("正在进行订单支付");
        financialFlow.setOutTradeNo(IdWorker.getId());
        baseMapper.insert(financialFlow);
    }

    @Override
    public void List(SearchDTO searchDTO) {

    }
}
