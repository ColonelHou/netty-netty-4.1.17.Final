
# $ thrift --gen java data.thrift 生成java
# $ thrift --gen py data.thrift # 下载官方依赖源码安装 sudo python setup.py install
namespace java io.netty.example.thrift.interfaces.thrift.generated
namespace py io.netty.example.thrift.interfaces.thrift.generated

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct Person {
    1:optional String username;
    2:optional int age;
    3:optional boolean married;
}

exception DataException {
    1:optional String message;
    2:optional String callStack;
    3:optional String date;
}

service PersonService {
    Person getPersonByUsername(1: required String username) throws (1:DataException dataException),

    void savePerson(1: required Person person) throws(1:DataException dataException)
}
