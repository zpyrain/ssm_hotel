package com.bdqn.dao;

import com.bdqn.entity.Checkout;

public interface CheckoutMapper {

    /**
     * 添加退房记录
     * @param checkout
     * @return
     */
    int addCheckout(Checkout checkout);

}
