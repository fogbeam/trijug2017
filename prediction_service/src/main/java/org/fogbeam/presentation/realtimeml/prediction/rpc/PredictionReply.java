// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: predictionApi.proto

package org.fogbeam.presentation.realtimeml.prediction.rpc;

/**
 * <pre>
 * The prediction response
 * </pre>
 *
 * Protobuf type {@code com.neuralobjects.backend.prediction.rpc.PredictionReply}
 */
public  final class PredictionReply extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.neuralobjects.backend.prediction.rpc.PredictionReply)
    PredictionReplyOrBuilder {
  // Use PredictionReply.newBuilder() to construct.
  private PredictionReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private PredictionReply() {
    predictedValue_ = false;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private PredictionReply(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            predictedValue_ = input.readBool();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionProto.internal_static_com_neuralobjects_backend_prediction_rpc_PredictionReply_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionProto.internal_static_com_neuralobjects_backend_prediction_rpc_PredictionReply_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply.class, org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply.Builder.class);
  }

  public static final int PREDICTEDVALUE_FIELD_NUMBER = 1;
  private boolean predictedValue_;
  /**
   * <code>optional bool predictedValue = 1;</code>
   */
  public boolean getPredictedValue() {
    return predictedValue_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (predictedValue_ != false) {
      output.writeBool(1, predictedValue_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (predictedValue_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(1, predictedValue_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply)) {
      return super.equals(obj);
    }
    org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply other = (org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply) obj;

    boolean result = true;
    result = result && (getPredictedValue()
        == other.getPredictedValue());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + PREDICTEDVALUE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getPredictedValue());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * The prediction response
   * </pre>
   *
   * Protobuf type {@code com.neuralobjects.backend.prediction.rpc.PredictionReply}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.neuralobjects.backend.prediction.rpc.PredictionReply)
      org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReplyOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionProto.internal_static_com_neuralobjects_backend_prediction_rpc_PredictionReply_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionProto.internal_static_com_neuralobjects_backend_prediction_rpc_PredictionReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply.class, org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply.Builder.class);
    }

    // Construct using org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      predictedValue_ = false;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionProto.internal_static_com_neuralobjects_backend_prediction_rpc_PredictionReply_descriptor;
    }

    public org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply getDefaultInstanceForType() {
      return org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply.getDefaultInstance();
    }

    public org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply build() {
      org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply buildPartial() {
      org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply result = new org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply(this);
      result.predictedValue_ = predictedValue_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply) {
        return mergeFrom((org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply other) {
      if (other == org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply.getDefaultInstance()) return this;
      if (other.getPredictedValue() != false) {
        setPredictedValue(other.getPredictedValue());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private boolean predictedValue_ ;
    /**
     * <code>optional bool predictedValue = 1;</code>
     */
    public boolean getPredictedValue() {
      return predictedValue_;
    }
    /**
     * <code>optional bool predictedValue = 1;</code>
     */
    public Builder setPredictedValue(boolean value) {
      
      predictedValue_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional bool predictedValue = 1;</code>
     */
    public Builder clearPredictedValue() {
      
      predictedValue_ = false;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:com.neuralobjects.backend.prediction.rpc.PredictionReply)
  }

  // @@protoc_insertion_point(class_scope:com.neuralobjects.backend.prediction.rpc.PredictionReply)
  private static final org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply();
  }

  public static org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PredictionReply>
      PARSER = new com.google.protobuf.AbstractParser<PredictionReply>() {
    public PredictionReply parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new PredictionReply(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<PredictionReply> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PredictionReply> getParserForType() {
    return PARSER;
  }

  public org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

