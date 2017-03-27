# Lucune-unit
可以反射自定义索引类型，自定义索引路径-默认类路为上两级下的indexWrite目录，中文分词，自定义搜索Query，分页搜索并缓存一部分数据
/*反射定义类*/
@LuceneAnnotation
public class User {
    @LuceneAnnotation(field = LuceneAnnotation.FieldEnum.StringField,fieldName = "id")
    private Integer id;
}


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
