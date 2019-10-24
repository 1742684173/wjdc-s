package com.aloogn.wjdc.bill.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailCriteria extends BillCriteria{

    protected Integer startCount;

    protected Integer endCount;


}