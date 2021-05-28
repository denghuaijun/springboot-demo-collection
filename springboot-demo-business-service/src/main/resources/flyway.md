# flyway学习
Flayway是一款数据库版本控制管理工具，，支持数据库版本自动升级，Migrations可以写成sql脚本，也可以写在java代码里；不仅支持Command Line和java api ，也支持Build构建工具和Spring boot，也可以在分布式环境下能够安全可靠安全地升级数据库，同时也支持失败恢复。

Flyway最核心的就是用于记录所有版本演化和状态的MetaData表，Flyway首次启动会创建默认名为flyway-schema-history的元素局表。 表中保存了版本，描述，要执行的sql脚本等；

sql脚本的格式：V+版本号 +双下划线+描述+后缀sql


命名
为了由Flyway接管，SQL迁移必须遵守以下命名模式：

版本化的迁移
前缀分隔符后缀
                      
   V 2 __ Add_new_table .sql
              
 版本说明
撤消迁移
前缀分隔符后缀
                      
   U 2 __ Add_new_table .sql
              
 版本说明
可重复的迁移
前缀分隔符后缀
                     
    R __ Add_new_table .sql
               
           描述
文件名由以下部分组成：

前缀：V用于版本控制（可配置）， U用于撤消（可配置）和 R用于可重复迁移（可配置）
版本：带点或下划线的版本可根据需要分隔尽可能多的部分（不适用于可重复的迁移）
分隔符：（__两个下划线）（可配置）
说明：下划线或空格将单词分开
后缀：.sql（配置）
可选的版本化SQL迁移也可以省略分隔符和描述。

~~~xml
flyway.baseline-description对执行迁移时基准版本的描述.
flyway.baseline-on-migrate当迁移时发现目标schema非空，而且带有没有元数据的表时，是否自动执行基准迁移，默认false.
flyway.baseline-version开始执行基准迁移时对现有的schema的版本打标签，默认值为1.
flyway.check-location检查迁移脚本的位置是否存在，默认false.
flyway.clean-on-validation-error当发现校验错误时是否自动调用clean，默认false.
flyway.enabled是否开启flywary，默认true.
flyway.encoding设置迁移时的编码，默认UTF-8.
flyway.ignore-failed-future-migration当读取元数据表时是否忽略错误的迁移，默认false.
flyway.init-sqls当初始化好连接时要执行的SQL.
flyway.locations迁移脚本的位置，默认db/migration.
flyway.out-of-order是否允许无序的迁移，默认false.
flyway.password目标数据库的密码.
flyway.placeholder-prefix设置每个placeholder的前缀，默认${.
flyway.placeholder-replacementplaceholders是否要被替换，默认true.
flyway.placeholder-suffix设置每个placeholder的后缀，默认}.
flyway.placeholders.[placeholder name]设置placeholder的value
flyway.schemas设定需要flywary迁移的schema，大小写敏感，默认为连接默认的schema.
flyway.sql-migration-prefix迁移文件的前缀，默认为V.
flyway.sql-migration-separator迁移脚本的文件名分隔符，默认__
flyway.sql-migration-suffix迁移脚本的后缀，默认为.sql
flyway.tableflyway使用的元数据表名，默认为schema_version
flyway.target迁移时使用的目标版本，默认为latest version
flyway.url迁移时使用的JDBC URL，如果没有指定的话，将使用配置的主数据源
flyway.user迁移数据库的用户名
flyway.validate-on-migrate迁移时是否校验，默认为true.
~~~