package com.bdqn.service;

import com.bdqn.entity.Checkout;

public interface CheckoutService {
    /**
     * 添加退房记录
     * @param checkout
     * @return
     */
    int addCheckout(Checkout checkout);
}
