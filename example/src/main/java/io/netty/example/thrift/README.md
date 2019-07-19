


> list  有序列表
> set   无序列表
> map
o

> odl文件生成双方语言的接口
struct News {
    1:i32 id;
    2:String title;
    3:String content;
    4:String mediaFrom;
    5:String author;
}

##### 若干个方法的集合
service IndexNewsOperatorServices {
    bool indexNews(indexNews)
    bool removeByNewId(1:i32 id)
}

typeof i32 int; // 定义别名