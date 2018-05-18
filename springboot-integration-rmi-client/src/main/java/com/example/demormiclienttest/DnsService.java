package com.example.demormiclienttest;

import java.rmi.Remote;

public interface DnsService extends Remote {

    String getName(String ip);
}
