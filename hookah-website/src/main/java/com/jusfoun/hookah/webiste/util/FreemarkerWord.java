package com.jusfoun.hookah.webiste.util;

import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Map;

public class FreemarkerWord {

	private Configuration configuration = null;

	public FreemarkerWord() {  
        configuration = new Configuration();  
        configuration.setDefaultEncoding("utf-8");  
    }  
	
	public ReturnData createDoc(Map<String,Object> dataMap, GoodsVo goodsVo) throws IOException, TemplateException {
        ReturnData returnData = new ReturnData<>();
	    configuration.setClassForTemplateLoading(this.getClass(), "/ftl/");
        Template t = null;
        try {
            switch (goodsVo.getGoodsType()){
                case 1:
                    t = configuration.getTemplate("api.ftl");
                    break;
                case 2:
                    t = configuration.getTemplate("dataModel.ftl");
                    break;
                case 4:
                    t = configuration.getTemplate("aTAloneSoft.ftl");
                    break;
                case 5:
                    t = configuration.getTemplate("aTSaaS.ftl");
                    break;
                case 6:
                    t = configuration.getTemplate("aSAloneSoft.ftl");
                    break;
                case 7:
                    t = configuration.getTemplate("aSSaaS.ftl");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Failed);
        }
        File cfgFile = ResourceUtils.getFile("classpath:");
        String absolutePath = cfgFile.getAbsolutePath();
        System.out.println("==========="+absolutePath);

//        String datePath = "\\" + DateUtils.toDateText(new Date(), "yyyyMMdd") + "\\";
//        String fileName =  goodsVo.getGoodsType() + "\\" + goodsVo.getGoodsName() + ".doc";
//        String tempath = this.getClass().getResource("/static/") + datePath + fileName;

        String docPath = absolutePath + File.separator + "static" + File.separator + "doc";

        if(!new File(docPath).exists()){
            new File(docPath).mkdir();
        }

        String tempath = docPath + File.separator + goodsVo.getGoodsName() + ".doc";

        File outFile = new File(tempath);
        
        if(outFile.exists()){
        	outFile.delete();
        }
        
        System.out.println(tempath);
        Writer out = null;
        FileOutputStream fos = null;
        try {  
            fos = new FileOutputStream(outFile);  
            OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");  
            out = new BufferedWriter(oWriter);   
        } catch (FileNotFoundException e1) {  
            e1.printStackTrace(); 
            returnData.setCode(ExceptionConst.Failed);
        }  
           
        try {  
            t.process(dataMap, out);
            out.flush();
            out.close();
            fos.close();
            returnData.setData(tempath);
        } catch (IOException | TemplateException e) {  
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Failed);
        }
		return returnData;
    }  
}
