# Lucune-unit
可以反射自定义索引类型
自定义索引路径-默认类路为上两级下的indexWrite目录
支持中文分词，自定义搜索Query，分页搜索并缓存一部分数据

@LuceneAnnotation(field = LuceneAnnotation.FieldEnum.StringField,fieldName = "id")
private Integer id;
