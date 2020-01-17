package com.qf.fastdfs;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastdfsApplicationTests {

	@Autowired
	private FastFileStorageClient client;

	@Test
	public void contextLoads() throws FileNotFoundException {
		File file = new File("d://kobe.jpg");
		//得到文件名
		String fileName = file.getName();
		//截取
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		FileInputStream inputStream = new FileInputStream(file);
		StorePath storePath = client.uploadFile(inputStream, file.length(), suffix, null);
		System.out.println(storePath.getGroup());
		System.out.println(storePath.getPath());
		System.out.println(storePath.getFullPath());

	}

}
