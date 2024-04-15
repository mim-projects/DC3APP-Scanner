package com.mim.util.api;

/**
 * Created by marcoisaac on 9/20/2016.
 */

public class WorkServer {

    public static String[] address = {"https://dc3-2018.jl.serv.net.mx/dc3BackEnd2/webresources/"
            , "http://cemex-5266592.jl.serv.net.mx/rest/webresources/"
            , "https://dc3-2019-toluca.jl.serv.net.mx/dc3BackEnd2/webresources/"
            , "https://dc3-2019-navojoa.jl.serv.net.mx/dc3BackEnd2/webresources/"
            , "https://dc3-2020-guadalajara.jl.serv.net.mx/dc3BackEnd2/webresources/"
            , "https://dc3-2020-monterrey.jl.serv.net.mx/dc3BackEnd2/webresources/"
            , "https://dc3-2020-orizaba.jl.serv.net.mx/dc3BackEnd2/webresources/"
            , "https://dc3-2020-meoqui.jl.serv.net.mx/dc3BackEnd2/webresources/"
            , "https://dc3-2020-grajales.jl.serv.net.mx/dc3BackEnd2/webresources/"
            , "https://dc3-2020-pachuca.jl.serv.net.mx/dc3BackEnd2/webresources/"
            ,"https://dc3-2021-os.jl.serv.net.mx/dc3BackEnd2/webresources/"

    };
    public static int POSICION = 0;
    //public static String BASE_URL = address[POSICION];

    public static String getBaseUrl(int pos) {
        return address[pos];
    }
}
