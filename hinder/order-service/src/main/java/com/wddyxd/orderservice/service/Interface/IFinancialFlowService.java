package com.wddyxd.orderservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.orderservice.pojo.DTO.FinancialFlowDTO;
import com.wddyxd.orderservice.pojo.entity.FinancialFlow;
import com.wddyxd.orderservice.pojo.entity.OrderMain;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:13
 **/

public interface IFinancialFlowService extends IService<FinancialFlow> {

    public void add(FinancialFlowDTO financialFlowDTO);

    public FinancialFlow paying(OrderMain orderMain);

    public void List(SearchDTO searchDTO);

}
