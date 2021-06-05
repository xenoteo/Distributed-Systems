# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: municipal_office.proto
"""Generated protocol buffer code."""
from google.protobuf.internal import enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
from google.protobuf import service as _service
from google.protobuf import service_reflection
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='municipal_office.proto',
  package='',
  syntax='proto3',
  serialized_options=b'\n\026xenoteo.com.github.gen\220\001\001',
  create_key=_descriptor._internal_create_key,
  serialized_pb=b'\n\x16municipal_office.proto\"8\n\x0eIssueArguments\x12\x18\n\x04type\x18\x01 \x01(\x0e\x32\n.IssueType\x12\x0c\n\x04name\x18\x02 \x01(\t\"\x1d\n\x0bIssueAnswer\x12\x0e\n\x06\x61nswer\x18\x01 \x01(\t*Z\n\tIssueType\x12\x17\n\x13ISSUE_TYPE_PASSPORT\x10\x00\x12\x1a\n\x16ISSUE_TYPE_CITIZENSHIP\x10\x01\x12\x18\n\x14ISSUE_TYPE_RESIDENCE\x10\x02\x32>\n\x0fMunicipalOffice\x12+\n\nCommission\x12\x0f.IssueArguments\x1a\x0c.IssueAnswerB\x1b\n\x16xenoteo.com.github.gen\x90\x01\x01\x62\x06proto3'
)

_ISSUETYPE = _descriptor.EnumDescriptor(
  name='IssueType',
  full_name='IssueType',
  filename=None,
  file=DESCRIPTOR,
  create_key=_descriptor._internal_create_key,
  values=[
    _descriptor.EnumValueDescriptor(
      name='ISSUE_TYPE_PASSPORT', index=0, number=0,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='ISSUE_TYPE_CITIZENSHIP', index=1, number=1,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='ISSUE_TYPE_RESIDENCE', index=2, number=2,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
  ],
  containing_type=None,
  serialized_options=None,
  serialized_start=115,
  serialized_end=205,
)
_sym_db.RegisterEnumDescriptor(_ISSUETYPE)

IssueType = enum_type_wrapper.EnumTypeWrapper(_ISSUETYPE)
ISSUE_TYPE_PASSPORT = 0
ISSUE_TYPE_CITIZENSHIP = 1
ISSUE_TYPE_RESIDENCE = 2



_ISSUEARGUMENTS = _descriptor.Descriptor(
  name='IssueArguments',
  full_name='IssueArguments',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='type', full_name='IssueArguments.type', index=0,
      number=1, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='name', full_name='IssueArguments.name', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=26,
  serialized_end=82,
)


_ISSUEANSWER = _descriptor.Descriptor(
  name='IssueAnswer',
  full_name='IssueAnswer',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='answer', full_name='IssueAnswer.answer', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=84,
  serialized_end=113,
)

_ISSUEARGUMENTS.fields_by_name['type'].enum_type = _ISSUETYPE
DESCRIPTOR.message_types_by_name['IssueArguments'] = _ISSUEARGUMENTS
DESCRIPTOR.message_types_by_name['IssueAnswer'] = _ISSUEANSWER
DESCRIPTOR.enum_types_by_name['IssueType'] = _ISSUETYPE
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

IssueArguments = _reflection.GeneratedProtocolMessageType('IssueArguments', (_message.Message,), {
  'DESCRIPTOR' : _ISSUEARGUMENTS,
  '__module__' : 'municipal_office_pb2'
  # @@protoc_insertion_point(class_scope:IssueArguments)
  })
_sym_db.RegisterMessage(IssueArguments)

IssueAnswer = _reflection.GeneratedProtocolMessageType('IssueAnswer', (_message.Message,), {
  'DESCRIPTOR' : _ISSUEANSWER,
  '__module__' : 'municipal_office_pb2'
  # @@protoc_insertion_point(class_scope:IssueAnswer)
  })
_sym_db.RegisterMessage(IssueAnswer)


DESCRIPTOR._options = None

_MUNICIPALOFFICE = _descriptor.ServiceDescriptor(
  name='MunicipalOffice',
  full_name='MunicipalOffice',
  file=DESCRIPTOR,
  index=0,
  serialized_options=None,
  create_key=_descriptor._internal_create_key,
  serialized_start=207,
  serialized_end=269,
  methods=[
  _descriptor.MethodDescriptor(
    name='Commission',
    full_name='MunicipalOffice.Commission',
    index=0,
    containing_service=None,
    input_type=_ISSUEARGUMENTS,
    output_type=_ISSUEANSWER,
    serialized_options=None,
    create_key=_descriptor._internal_create_key,
  ),
])
_sym_db.RegisterServiceDescriptor(_MUNICIPALOFFICE)

DESCRIPTOR.services_by_name['MunicipalOffice'] = _MUNICIPALOFFICE

MunicipalOffice = service_reflection.GeneratedServiceType('MunicipalOffice', (_service.Service,), dict(
  DESCRIPTOR = _MUNICIPALOFFICE,
  __module__ = 'municipal_office_pb2'
  ))

MunicipalOffice_Stub = service_reflection.GeneratedServiceStubType('MunicipalOffice_Stub', (MunicipalOffice,), dict(
  DESCRIPTOR = _MUNICIPALOFFICE,
  __module__ = 'municipal_office_pb2'
  ))


# @@protoc_insertion_point(module_scope)
