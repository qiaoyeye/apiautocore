package com.core.constant;
/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年3月4日 下午3:41:00 
 * 类说明 
 */
public enum ExcelHeaders {
	StatusCode(){
		@Override
        public String toString() {
            return "StatusCode";
        }
	},
	ResponseJson(){
		@Override
        public String toString() {
            return "ResponseJson";
        }
	},
	ResultCode(){
		@Override
        public String toString() {
            return "ResultCode";
        }
	},
	Result(){
		@Override
        public String toString() {
            return "Result";
        }
	},
	RUN(){
		@Override
        public String toString() {
            return "RUN";
        }
	};
	ExcelHeaders() {
    }
}
