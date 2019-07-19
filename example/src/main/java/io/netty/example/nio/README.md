
java.io  最核心概念是流, 面向流的编程; 要么输出流,要么输入流

java.nio JDK1.4 面向块(缓冲区)编程
```$xslt
1. Selector
2. Channel 双向的; 在linux系统底层通道是双向的;
3. Buffer  一块内存,底层是数组; 
   a. limit    : 第一个不能被读/写元素索引, 不能超capacity;  10个大小buffer中写了了6个,position是6;  limit也是6
   b. position : 下一个要读/写元素的索引, 不会超limit; 开始写position是0; 
   c. capacity : 包含元素个数; allocate(10)最多放10个元素; （指向buffer最后一个的下一个索引）不变的
   mark <= position <= limit <= capacity
   rewind : mark清除, position设置为0

selector 同时对多个channel, 通过对事件的判断进行响应, 
channel1有事件->selector转到他进行响应

除了数组外, Buffer还提供对于数据的结构化访问, 并且可以追踪到系统读写过程;
```