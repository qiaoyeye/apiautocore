package com.core.constant;
/** 
 * @author QiaoJiafei 
 * @version ����ʱ�䣺2016��3��4�� ����3:41:00 
 * ��˵�� 
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
