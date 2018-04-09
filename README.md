# SpringBoot
Mybatis整合 phoenix

在phoenix中创建poiPh表及其索引
//创建poiPh
create table "poiPh" (Rowkey VARBINARY(50) not null primary key, "geoNum" bigint,"name" varchar(255),"otherid" varchar(40),"starting" varchar(40),"typecode" varchar(20),"type" varchar(255)) ;
//创建索引表
create index "poiph_geoNum_typecode_idx" on "poiPh" ("geoNum", "typecode");

基于geonum，level，type进行分页查询poi

请求url示例：
http://localhost:9090/phoenix/getPoi?geoNum=526548410296434688&level=16&offset=0&limit=50&types=110200