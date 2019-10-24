package com.aloogn.wjdc.bill.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetail extends Bill{

    private BillLabel billLabel;

    private BillSort billSort;
}
