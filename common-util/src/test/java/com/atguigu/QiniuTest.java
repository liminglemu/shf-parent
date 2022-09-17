package com.atguigu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import org.junit.Test;
import util.QiniuUtils;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/3 21:14
 */
public class QiniuTest {
   /* @Test
    public void testUp() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.autoZone());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        //去个人中心密钥管理找
        String accessKey = "5tbXTKAj0wdM_L4qfLqCiix87BQgr8BDkpP9yACs";
        String secretKey = "a8q-EIqLtxZrp2h0_WIMS0j0LsuAEoFdAa0iTcu7";
        //空间的名字
        String bucket = "ssm-shf";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //设置本地文件路径
        String localFilePath = "D:/DownLoad/06_尚好房/七牛云上传图片/63132301f33da.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }*/

    //测试删除七牛云上的图片
    @Test
    public void testDelete() {
        Configuration cfg = new Configuration(Zone.autoZone());
        //去个人中心密钥管理找
        String accessKey = "5tbXTKAj0wdM_L4qfLqCiix87BQgr8BDkpP9yACs";
        String secretKey = "a8q-EIqLtxZrp2h0_WIMS0j0LsuAEoFdAa0iTcu7";
        String bucket = "ssm-shf";

        String key = "FrKTdjU6ATpdGizf0R36Nhkvora0";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    @Test
    public void test() {
        QiniuUtils.upload2Qiniu("D:\\DownLoad\\06_尚好房\\七牛云上传图片\\63132301f33da.jpg", "龍之女");
    }

}
