package com.liu;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.*;

public class test {

    @Test
    public void test(){
        System.out.print(test1());

    }

    public static int test1() {
        int a = 20;
        try {
            return a + 25;
        } catch (Exception e) {
            System.out.println("test catch exception");
        } finally {
            System.out.print(a + " ");
            a = a + 10;
        }
        return a;
    }
}
