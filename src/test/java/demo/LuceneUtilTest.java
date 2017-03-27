package demo;

import net.hncu.notes.lucene.util.AbstractLuceneIndex;
import net.hncu.notes.lucene.util.LuceneAnnotation;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by doobo@foxmail.com on 2017/3/9.
 */
public class LuceneUtilTest {

    /**
     * 测试反射User类添加数据到索引库，
     * 索引库可以到IKAnalyzer.cfg.xml文件配置
     */
    @Test
    public void testLuceneIndex(){
        User user;
        ArrayList list = new ArrayList();
        for (int i = 1; i <= 100; i++) {
            user = new User(i,"unit and book",
                    "管理","世界 友好 为什么 Hello World!",0,0);
            list.add(user);
        }
        AbstractLuceneIndex.addDocument(list,AbstractLuceneIndex.getIndexWriter());
    }


    /**
     * 测设分页搜索
     * @throws Exception
     */
    @Test
    public void testSeachLuceneIndex() throws Exception {
        //编写查询语句
        Query q1 = new TermQuery(new Term("password", "世界"));

        //测试分页查询
        for (int i = 1; i < 25; i++) {
            //查询结果
            TreeMap treeMap = AbstractLuceneIndex.doSearch(q1,i,5);
            if(treeMap.get("doc") == null)
                return;

            //解析数据结果
            System.out.println("总页数：" + treeMap.get("pageCount")
                    + "\t当前页：" + treeMap.get("curPage")
                    +"\t总记录数"+treeMap.get("count"));
            for (Document doc : (ArrayList<Document>)treeMap.get("doc")) {
                System.out.print("用户ID：" + doc.get("id"));
                System.out.print("用户昵称：" + doc.get("nickname"));
                System.out.print("\t用户密码：" + doc.get("password"));
                System.out.println("最后修改时间：" + doc.get("time"));
            }
            System.out.println("=========================================");

        }
    }

}
