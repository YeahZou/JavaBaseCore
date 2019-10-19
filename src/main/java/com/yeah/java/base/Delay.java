package com.yeah.java.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Delay {

	public static void main(String[] args) throws Exception {
		
		// 需要环境支持 GUI
		/*Robot  r   =   new   Robot(); 
        System.out.println( "延时前:"+new Date().toString()  ); 
        r.delay(   1000 * 60   );
        System.out.println( "延时前:"+new Date().toString()  ); 
        r.delay(   1000 * 60   );
        System.out.println( "延时前:"+new Date().toString()  ); 
        r.delay(   1000 * 60   );
        r.delay(   1000 * 60   );
        r.delay(   1000 * 60   );
        r.delay(   1000 * 60   );
        r.delay(   1000 * 60   );
        r.delay(   1000 * 60   );
        r.delay(   1000 * 60   );
        r.delay(   1000 * 60   );*/

        System.out.println( "延时前:"+new Date().toString()  ); 
        
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("退出");
                this.cancel();
            }
        }, 1000 * 60 * 10);
        System.out.println("本程序存在10分钟后自动退出");*/
        
        TimeUnit.MINUTES.sleep(1);
        Thread.sleep(5000);
        System.out.println( "延时后" + new Date().toString()  ); 
	}

}
