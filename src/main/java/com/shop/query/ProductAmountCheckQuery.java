package com.shop.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductAmountCheckQuery {
    private List<Long> ids;
}
