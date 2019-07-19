package io.netty.example.thrift.server;

import io.netty.example.thrift.interfaces.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {
    public static void main(String[] args) throws TTransportException {
        // TSimpleServer简单的单线程; TThreadPoolServer, THsHaServer半同步半异步的处理
        TNonblockingServerSocket socket = new TNonblockingServerSocket(9999);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        // 协议层
        arg.protocolFactory(new TCompactProtocol.Factory());
        // 传输方式: 传输层 TSocket , TFileTransport, TMemory, TZlib
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));

        TServer server = new THsHaServer(arg);
        System.out.println("Thrift Server start...");
        server.serve();
    }
}
