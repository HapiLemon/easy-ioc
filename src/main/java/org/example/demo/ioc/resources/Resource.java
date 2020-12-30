package org.example.demo.ioc.resources;

import java.io.InputStream;

/**
 * 既然要从配置文件中读取，自然要先能根据配置文件的名字找到这个文件，然后打开它，生成一个InputStream共我们读取使用。
 */
public interface Resource {
    InputStream getInputStream() throws Exception;
}
