/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\git\\psw\\ScanerServer\\src\\com\\scan\\service\\IScan.aidl
 */
package com.scan.service;
public interface IScan extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.scan.service.IScan
{
private static final java.lang.String DESCRIPTOR = "com.scan.service.IScan";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.scan.service.IScan interface,
 * generating a proxy if needed.
 */
public static com.scan.service.IScan asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.scan.service.IScan))) {
return ((com.scan.service.IScan)iin);
}
return new com.scan.service.IScan.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_init:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.init();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_close:
{
data.enforceInterface(DESCRIPTOR);
this.close();
reply.writeNoException();
return true;
}
case TRANSACTION_scan:
{
data.enforceInterface(DESCRIPTOR);
this.scan();
reply.writeNoException();
return true;
}
case TRANSACTION_setOnResultListener:
{
data.enforceInterface(DESCRIPTOR);
com.scan.service.IScanResult _arg0;
_arg0 = com.scan.service.IScanResult.Stub.asInterface(data.readStrongBinder());
this.setOnResultListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setChar:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.setChar(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.scan.service.IScan
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public int init() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_init, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//init scaner engine

@Override public void close() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_close, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void scan() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_scan, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//start scanning 

@Override public void setOnResultListener(com.scan.service.IScanResult iLister) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((iLister!=null))?(iLister.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setOnResultListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//listen scan result

@Override public void setChar(java.lang.String charSetName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(charSetName);
mRemote.transact(Stub.TRANSACTION_setChar, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_init = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_close = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_scan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setOnResultListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setChar = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public int init() throws android.os.RemoteException;
//init scaner engine

public void close() throws android.os.RemoteException;
public void scan() throws android.os.RemoteException;
//start scanning 

public void setOnResultListener(com.scan.service.IScanResult iLister) throws android.os.RemoteException;
//listen scan result

public void setChar(java.lang.String charSetName) throws android.os.RemoteException;
}
