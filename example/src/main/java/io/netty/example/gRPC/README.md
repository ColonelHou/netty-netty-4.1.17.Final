
> 基于protobuf3 的RPC框架
> 基于HTTP/2




> gradle插件生成java到src目录下
```$xslt
protobuf {
//    generatedFilesBaseDir = "$projectDir/src/generated"
    generatedFilesBaseDir = "src"
    protoc {
        artifact = "com.google.protobuf:protoc:3.7.1"
    }
    plugins {
        grpc {
            artifact = 'io.grpc:protoc-gen-grpc-java:1.22.1'
        }
    }
    generateProtoTasks {
        all()*.plugins {
            grpc {
                outputSubDir = "java"
            }
        }
    }
}

```