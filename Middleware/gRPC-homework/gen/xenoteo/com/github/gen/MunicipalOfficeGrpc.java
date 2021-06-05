package xenoteo.com.github.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: municipal_office.proto")
public final class MunicipalOfficeGrpc {

  private MunicipalOfficeGrpc() {}

  public static final String SERVICE_NAME = "MunicipalOffice";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments,
      xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer> getCommissionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Commission",
      requestType = xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments.class,
      responseType = xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments,
      xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer> getCommissionMethod() {
    io.grpc.MethodDescriptor<xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments, xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer> getCommissionMethod;
    if ((getCommissionMethod = MunicipalOfficeGrpc.getCommissionMethod) == null) {
      synchronized (MunicipalOfficeGrpc.class) {
        if ((getCommissionMethod = MunicipalOfficeGrpc.getCommissionMethod) == null) {
          MunicipalOfficeGrpc.getCommissionMethod = getCommissionMethod = 
              io.grpc.MethodDescriptor.<xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments, xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "MunicipalOffice", "Commission"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer.getDefaultInstance()))
                  .setSchemaDescriptor(new MunicipalOfficeMethodDescriptorSupplier("Commission"))
                  .build();
          }
        }
     }
     return getCommissionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MunicipalOfficeStub newStub(io.grpc.Channel channel) {
    return new MunicipalOfficeStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MunicipalOfficeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MunicipalOfficeBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MunicipalOfficeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MunicipalOfficeFutureStub(channel);
  }

  /**
   */
  public static abstract class MunicipalOfficeImplBase implements io.grpc.BindableService {

    /**
     */
    public void commission(xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments request,
        io.grpc.stub.StreamObserver<xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer> responseObserver) {
      asyncUnimplementedUnaryCall(getCommissionMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCommissionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments,
                xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer>(
                  this, METHODID_COMMISSION)))
          .build();
    }
  }

  /**
   */
  public static final class MunicipalOfficeStub extends io.grpc.stub.AbstractStub<MunicipalOfficeStub> {
    private MunicipalOfficeStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MunicipalOfficeStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MunicipalOfficeStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MunicipalOfficeStub(channel, callOptions);
    }

    /**
     */
    public void commission(xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments request,
        io.grpc.stub.StreamObserver<xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCommissionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MunicipalOfficeBlockingStub extends io.grpc.stub.AbstractStub<MunicipalOfficeBlockingStub> {
    private MunicipalOfficeBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MunicipalOfficeBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MunicipalOfficeBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MunicipalOfficeBlockingStub(channel, callOptions);
    }

    /**
     */
    public xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer commission(xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments request) {
      return blockingUnaryCall(
          getChannel(), getCommissionMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MunicipalOfficeFutureStub extends io.grpc.stub.AbstractStub<MunicipalOfficeFutureStub> {
    private MunicipalOfficeFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MunicipalOfficeFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MunicipalOfficeFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MunicipalOfficeFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer> commission(
        xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments request) {
      return futureUnaryCall(
          getChannel().newCall(getCommissionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_COMMISSION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MunicipalOfficeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MunicipalOfficeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_COMMISSION:
          serviceImpl.commission((xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueArguments) request,
              (io.grpc.stub.StreamObserver<xenoteo.com.github.gen.MunicipalOfficeOuterClass.IssueAnswer>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MunicipalOfficeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MunicipalOfficeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return xenoteo.com.github.gen.MunicipalOfficeOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MunicipalOffice");
    }
  }

  private static final class MunicipalOfficeFileDescriptorSupplier
      extends MunicipalOfficeBaseDescriptorSupplier {
    MunicipalOfficeFileDescriptorSupplier() {}
  }

  private static final class MunicipalOfficeMethodDescriptorSupplier
      extends MunicipalOfficeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MunicipalOfficeMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MunicipalOfficeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MunicipalOfficeFileDescriptorSupplier())
              .addMethod(getCommissionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
