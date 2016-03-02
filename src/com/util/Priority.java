package com.util;
/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月20日 上午10:38:16 
 * 类说明 
 */
public enum Priority {
	P1(){
		@Override
        public String toString() {
            return "P1";
        }
	},
	P2(){
		@Override
        public String toString() {
            return "P2";
        }
	},
	P3(){
		@Override
        public String toString() {
            return "P3";
        }
	},
	ALL(){
		@Override
        public String toString() {
            return "ALL";
        }
	};
    Priority() {
    }
	
    
}
