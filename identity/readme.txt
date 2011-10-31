Note:

When use the Postgres: need to update the file:
/home/thanh_vucong/java/etkProjects/etk-model/identity/src/test/resources/conf/exo.portal.component.identity-configuration.xml


 <component>
      <key>org.exoplatform.services.database.HibernateService</key>
      <jmx-name>database:type=HibernateService</jmx-name>
      <type>org.exoplatform.services.database.impl.HibernateServiceImpl</type>
      <init-params>
        <properties-param>
            <name>hibernate.properties</name>
            <description>Default Hibernate Service</description>
            <property name="hibernate.hbm2ddl.auto" value="create-drop"/>
            <property name="hibernate.show_sql" value="false"/>
            <property name="hibernate.current_session_context_class" value="thread"/>
            <property name="hibernate.cache.use_second_level_cache" value="true"/>
            <property name="hibernate.cache.use_query_cache" value="true"/>
            <!--CHANGEME HashtableCacheProvider shold not be used in production env-->
            <property name="hibernate.cache.provider_class" value="org.hibernate.cache.HashtableCacheProvider"/>
            <property name="hibernate.connection.datasource" value="jdbcidm"/>
            <!--<property name="hibernate.dialect" value="org.hibernate.dialect.HSQLDialect"/>-->
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
            <property name="hibernate.c3p0.min_size" value="5"/>
            <property name="hibernate.c3p0.max_size" value="20"/>
            <property name="hibernate.c3p0.timeout" value="1800"/>
            <property name="hibernate.c3p0.max_statements" value="50"/>
         </properties-param>
      </init-params>
   </component>

->>>

 <component>
      <key>org.exoplatform.services.database.HibernateService</key>
      <jmx-name>database:type=HibernateService</jmx-name>
      <type>org.exoplatform.services.database.impl.HibernateServiceImpl</type>
      <init-params>
         <!--
         <properties-param>
            <name>hibernate.properties</name>
            <description>Default Hibernate Service</description>
            <property name="hibernate.hbm2ddl.auto" value="create-drop"/>
            <property name="hibernate.show_sql" value="false"/>
            <property name="hibernate.current_session_context_class" value="thread"/>
            <property name="hibernate.cache.use_second_level_cache" value="true"/>
            <property name="hibernate.cache.use_query_cache" value="true"/>
            <!--CHANGEME HashtableCacheProvider shold not be used in production env-->
            <property name="hibernate.cache.provider_class" value="org.hibernate.cache.HashtableCacheProvider"/>
            <property name="hibernate.connection.datasource" value="jdbcidm"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.HSQLDialect"/>
            <property name="hibernate.c3p0.min_size" value="5"/>
            <property name="hibernate.c3p0.max_size" value="20"/>
            <property name="hibernate.c3p0.timeout" value="1800"/>
            <property name="hibernate.c3p0.max_statements" value="50"/>
         </properties-param>-->

         <properties-param>
            <name>hibernate.properties</name>
            <description>Default Hibernate Service</description>
            <property name="hibernate.hbm2ddl.auto" value="create-drop"/>
            <property name="hibernate.show_sql" value="false"/>
            <property name="hibernate.current_session_context_class" value="thread"/>
            <property name="hibernate.cache.use_second_level_cache" value="true"/>
            <property name="hibernate.cache.use_query_cache" value="true"/>
            <!--CHANGEME HashtableCacheProvider shold not be used in production env-->
            <property name="hibernate.cache.provider_class" value="org.hibernate.cache.HashtableCacheProvider"/>
            <property name="hibernate.connection.datasource" value="jdbcidm"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
            <property name="hibernate.c3p0.min_size" value="5"/>
            <property name="hibernate.c3p0.max_size" value="20"/>
            <property name="hibernate.c3p0.timeout" value="1800"/>
            <property name="hibernate.c3p0.max_statements" value="50"/>
         </properties-param>
      </init-params>
   </component>



AND

<external-component-plugins>
    <target-component>org.exoplatform.services.naming.InitialContextInitializer</target-component>
    <component-plugin>
      <name>bind.datasource</name>
      <set-method>addPlugin</set-method>
      <type>org.exoplatform.services.naming.BindReferencePlugin</type>
      <init-params>
        <value-param>
          <name>bind-name</name>
          <value>jdbcidm</value>
        </value-param>
        <value-param>
          <name>class-name</name>
          <value>javax.sql.DataSource</value>
        </value-param>
        <value-param>
          <name>factory</name>
          <value>org.apache.commons.dbcp.BasicDataSourceFactory</value>
        </value-param>
        <!--
        <properties-param>
          <name>ref-addresses</name>
          <description>ref-addresses</description>
          <!--<property name="driverClassName" value="org.hsqldb.jdbcDriver"/>
          <property name="url" value="jdbc:hsqldb:file:${gatein.test.tmp.dir}/db/data/jdbcidm"/>
          <property name="username" value="sa"/>
          <property name="password" value=""/>-->

          <property name="driverClassName" value="org.postgresql.Driver"/>
          <property name="url" value="jdbc:postgresql://localhost/exodb"/>
          <property name="username" value="root"/>
          <property name="password" value="gtn"/>
        </properties-param>
      </init-params>
    </component-plugin>
  </external-component-plugins>
