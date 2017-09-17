package org.fogbeam.presentation.realtimeml.prediction.rpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * The prediction service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: predictionApi.proto")
public final class PredictionServiceGrpc {

  private PredictionServiceGrpc() {}

  public static final String SERVICE_NAME = "com.neuralobjects.backend.prediction.rpc.PredictionService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest,
      org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply> METHOD_MAKE_PREDICTION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.neuralobjects.backend.prediction.rpc.PredictionService", "MakePrediction"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PredictionServiceStub newStub(io.grpc.Channel channel) {
    return new PredictionServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PredictionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PredictionServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static PredictionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PredictionServiceFutureStub(channel);
  }

  /**
   * <pre>
   * The prediction service definition.
   * </pre>
   */
  public static abstract class PredictionServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * make a prediction
     * </pre>
     */
    public void makePrediction(org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest request,
        io.grpc.stub.StreamObserver<org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MAKE_PREDICTION, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_MAKE_PREDICTION,
            asyncUnaryCall(
              new MethodHandlers<
                org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest,
                org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply>(
                  this, METHODID_MAKE_PREDICTION)))
          .build();
    }
  }

  /**
   * <pre>
   * The prediction service definition.
   * </pre>
   */
  public static final class PredictionServiceStub extends io.grpc.stub.AbstractStub<PredictionServiceStub> {
    private PredictionServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * make a prediction
     * </pre>
     */
    public void makePrediction(org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest request,
        io.grpc.stub.StreamObserver<org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MAKE_PREDICTION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The prediction service definition.
   * </pre>
   */
  public static final class PredictionServiceBlockingStub extends io.grpc.stub.AbstractStub<PredictionServiceBlockingStub> {
    private PredictionServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * make a prediction
     * </pre>
     */
    public org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply makePrediction(org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MAKE_PREDICTION, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The prediction service definition.
   * </pre>
   */
  public static final class PredictionServiceFutureStub extends io.grpc.stub.AbstractStub<PredictionServiceFutureStub> {
    private PredictionServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * make a prediction
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply> makePrediction(
        org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MAKE_PREDICTION, getCallOptions()), request);
    }
  }

  private static final int METHODID_MAKE_PREDICTION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PredictionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PredictionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MAKE_PREDICTION:
          serviceImpl.makePrediction((org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest) request,
              (io.grpc.stub.StreamObserver<org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply>) responseObserver);
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

  private static final class PredictionServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PredictionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PredictionServiceDescriptorSupplier())
              .addMethod(METHOD_MAKE_PREDICTION)
              .build();
        }
      }
    }
    return result;
  }
}
