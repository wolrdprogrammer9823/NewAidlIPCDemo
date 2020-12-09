// IRemoteService.aidl
package com.wolfsea.newaidlipcdemo;
import com.wolfsea.newaidlipcdemo.MyData;

interface IRemoteService {

    int getPid();

    MyData getMyData();
}