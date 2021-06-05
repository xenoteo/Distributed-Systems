# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

import municipal_office_pb2 as municipal__office__pb2


class MunicipalOfficeStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.Commission = channel.unary_unary(
                '/MunicipalOffice/Commission',
                request_serializer=municipal__office__pb2.IssueArguments.SerializeToString,
                response_deserializer=municipal__office__pb2.IssueAnswer.FromString,
                )


class MunicipalOfficeServicer(object):
    """Missing associated documentation comment in .proto file."""

    def Commission(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_MunicipalOfficeServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'Commission': grpc.unary_unary_rpc_method_handler(
                    servicer.Commission,
                    request_deserializer=municipal__office__pb2.IssueArguments.FromString,
                    response_serializer=municipal__office__pb2.IssueAnswer.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'MunicipalOffice', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class MunicipalOffice(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def Commission(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/MunicipalOffice/Commission',
            municipal__office__pb2.IssueArguments.SerializeToString,
            municipal__office__pb2.IssueAnswer.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)
