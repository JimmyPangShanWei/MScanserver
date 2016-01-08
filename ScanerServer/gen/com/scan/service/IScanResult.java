/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\git\\psw\\ScanerServer\\src\\com\\scan\\service\\IScanResult.aidl
 */
package com.scan.service;
public interface IScanResult extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.scan.service.IScanResult
{
private static final java.lang.String DESCRIPTOR = "com.scan.service.IScanResult";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.scan.service.IScanResult interface,
 * generating a proxy if needed.
 */
public static com.scan.service.IScanResult asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.scan.service.IScanResult))) {
return ((com.scan.service.IScanResult)iin);
}
return new com.scan.service.IScanResult.Stub.Proxy(obj);
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
case TRANSACTION_onListener:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte[] _arg1;
_arg1 = data.createByteArray();
this.onListener(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.scan.service.IScanResult
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
@Override public void onListener(java.lang.String barcode, byte[] bar) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(barcode);
_data.writeByteArray(bar);
mRemote.transact(Stub.TRANSACTION_onListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void onListener(java.lang.String barcode, byte[] bar) throws android.os.RemoteException;
}
