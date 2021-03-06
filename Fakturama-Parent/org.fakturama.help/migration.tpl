<?xml version="1.0" encoding="UTF-8"?>
<Graph author="Ralf" created="Wed Jan 17 01:35:08 CET 2018" guiVersion="4.8.0" id="1516149394050" licenseCode="RegCode" name="migration" showComponentDetails="true">
<Global>
<Metadata id="Metadata5">
<Record fieldDelimiter=";" name="ADDRESS_METADATA" recordDelimiter="\n" type="delimited">
<Field name="ID" size="64" type="long"/>
<Field name="CITY" size="255" type="string"/>
<Field name="CITYADDON" size="255" type="string"/>
<Field name="COUNTRYCODE" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" type="boolean"/>
<Field name="MANUALADDRESS" size="4096" type="string"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="STREET" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="ZIP" size="255" type="string"/>
</Record>
</Metadata>
<Metadata id="Metadata6">
<Record fieldDelimiter=";" name="BANKACCOUNT_METADATA" recordDelimiter="\n" type="delimited">
<Field name="ID" size="64" type="long"/>
<Field name="ACCOUNTHOLDER" size="255" type="string"/>
<Field name="BANKCODE" size="32" type="integer"/>
<Field name="BANKNAME" size="255" type="string"/>
<Field name="BIC" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" type="boolean"/>
<Field name="IBAN" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
</Record>
</Metadata>
<Metadata id="Metadata1">
<Record fieldDelimiter=";" name="CATEGORY_METADATA" recordDelimiter="\n" recordSize="-1" type="delimited">
<Field eofAsDelimiter="false" name="ID" nullable="true" shift="0" size="64" type="long"/>
<Field eofAsDelimiter="false" name="CATEGORYNAME" nullable="true" shift="0" size="31" type="string"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="DATEADDED" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" name="DELETED" nullable="true" shift="0" size="0" type="boolean"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="MODIFIED" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" name="MODIFIEDBY" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" name="NAME" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="VALIDFROM" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="VALIDTO" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" name="ABSTRACTCATEGORY_PARENT" nullable="true" shift="0" size="64" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata7">
<Record fieldDelimiter=";" name="CEFACTCODE_METADATA" recordDelimiter="\n" type="delimited">
<Field name="ID" size="64" type="long"/>
<Field name="ABBREVIATION_DE" size="255" type="string"/>
<Field name="ABBREVIATION_EN" size="255" type="string"/>
<Field name="CODE" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" type="boolean"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="NAME_DE" size="255" type="string"/>
<Field name="TARGET" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
</Record>
</Metadata>
<Metadata id="Metadata4">
<Record fieldDelimiter=";" name="ITEMACCOUNTTYPE_METADATA" recordDelimiter="\n" type="delimited">
<Field name="ID" size="64" type="long"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" type="boolean"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="T_VALUE" size="255" type="string"/>
<Field name="ITEMACCOUNTTYPE_CATEGORY" size="64" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata9">
<Record fieldDelimiter=";" name="PAYMENT_METADATA" recordDelimiter="\n" type="delimited">
<Field name="ID" size="64" type="long"/>
<Field name="CODE" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" type="boolean"/>
<Field name="DEPOSITTEXT" size="4096" type="string"/>
<Field name="DESCRIPTION" size="8192" type="string"/>
<Field name="DISCOUNTDAYS" size="32" type="integer"/>
<Field name="DISCOUNTVALUE" size="64" type="number"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="NETDAYS" size="32" type="integer"/>
<Field name="PAIDTEXT" size="4096" type="string"/>
<Field name="UNPAIDTEXT" size="4096" type="string"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="FK_CATEGORY" size="64" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata0">
<Record fieldDelimiter=";" name="PRODUCT_METADATA" recordDelimiter="\n" recordSize="-1" type="delimited">
<Field eofAsDelimiter="false" name="ID" nullable="true" shift="0" size="64" type="long"/>
<Field eofAsDelimiter="false" name="BLOCK1" nullable="true" shift="0" size="32" type="integer"/>
<Field eofAsDelimiter="false" name="BLOCK2" nullable="true" shift="0" size="32" type="integer"/>
<Field eofAsDelimiter="false" name="BLOCK3" nullable="true" shift="0" size="32" type="integer"/>
<Field eofAsDelimiter="false" name="BLOCK4" nullable="true" shift="0" size="32" type="integer"/>
<Field eofAsDelimiter="false" name="BLOCK5" nullable="true" shift="0" size="32" type="integer"/>
<Field eofAsDelimiter="false" name="CDF01" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" name="CDF02" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" name="CDF03" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" name="COSTPRICE" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="DATEADDED" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" name="DELETED" nullable="true" shift="0" size="0" type="boolean"/>
<Field eofAsDelimiter="false" name="DESCRIPTION" nullable="true" shift="0" size="1073741824" type="string"/>
<Field eofAsDelimiter="false" name="GTIN" nullable="true" shift="0" size="64" type="long"/>
<Field eofAsDelimiter="false" name="ITEMNUMBER" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="MODIFIED" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" name="MODIFIEDBY" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" name="NAME" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" name="PICTURE" nullable="true" shift="0" size="1073741824" type="byte"/>
<Field eofAsDelimiter="false" name="PRICE1" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" name="PRICE2" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" name="PRICE3" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" name="PRICE4" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" name="PRICE5" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" name="QUANTITY" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" name="QUANTITYUNIT" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" name="SELLINGUNIT" nullable="true" shift="0" size="32" type="integer"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="VALIDFROM" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="VALIDTO" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" name="WEBSHOPID" nullable="true" shift="0" size="64" type="long"/>
<Field eofAsDelimiter="false" name="WEIGHT" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" name="FK_CATEGORY" nullable="true" shift="0" size="64" type="long"/>
<Field eofAsDelimiter="false" name="FK_VAT" nullable="true" shift="0" size="64" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata3">
<Record fieldDelimiter=";" name="PRODUCTBLOCK_METADATA" previewAttachmentCharset="UTF-8" recordDelimiter="\n" type="delimited">
<Field name="ID" size="64" type="long"/>
<Field name="BLOCK" size="32" type="integer"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" type="boolean"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="PRICE" size="64" type="number"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="FK_PRODUCT" size="64" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata2">
<Record fieldDelimiter=";" name="VAT_METADATA" recordDelimiter="\n" recordSize="-1" type="delimited">
<Field eofAsDelimiter="false" name="ID" nullable="true" shift="0" size="64" type="long"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="DATEADDED" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" name="DELETED" nullable="true" shift="0" size="0" type="boolean"/>
<Field eofAsDelimiter="false" name="DESCRIPTION" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="MODIFIED" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" name="MODIFIEDBY" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" name="NAME" nullable="true" shift="0" size="255" type="string"/>
<Field eofAsDelimiter="false" name="SALESEQUALIZATIONTAX" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" name="TAXVALUE" nullable="true" shift="0" size="64" type="number"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="VALIDFROM" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" format="yyyy-MM-dd" name="VALIDTO" nullable="true" shift="0" size="10" type="date"/>
<Field eofAsDelimiter="false" name="FK_CATEGORY" nullable="true" shift="0" size="64" type="long"/>
</Record>
</Metadata>
<Connection database="MYSQL" dbURL="jdbc:mysql://{{fakturama.target.mysql.host}}/{{fakturama.target.mysql.database}}" id="JDBC1" jdbcSpecific="MYSQL" name="FktMysql" password="{{fakturama.target.mysql.password}}" type="JDBC" user="{{fakturama.target.mysql.user}}"/>
<Connection database="HSQLDB" dbURL="jdbc:hsqldb:{{fakturama.workdir}}/Database/Database" id="JDBC0" jdbcSpecific="HSQLDB" name="hsqldb" type="JDBC" user="sa"/>
<GraphParameters>
<GraphParameterFile fileURL="workspace.prm"/>
</GraphParameters>
<RichTextNote backgroundColor="FAF6D6" folded="false" fontSize="medium" height="408" id="Note0" textColor="444444" width="525" x="727" y="95">
<attr name="text"><![CDATA[h2. Migration HSQLDB nach MySQL für Fakturama v2.x

Dieser Graph dient dazu, Daten aus einer HSQL-DB in eine MySQL-DB zu überführen. Dabei sind folgende Voraussetzungen zu beachten:
- Fakturama-Version 2.0.0 oder Version 2.0.0.1 wird verwendet
- die MySQL-Datenbank wurde durch Fakturama (einmaliger Start) angelegt (dazu muß zunächst das .fakturama2-Verzeichnis im Homeverzeichnis gelöscht werden, damit der Install-Dialog wieder erscheint)

Folgende Tabellen wurden weggelassen, da sie in der Version 2.0.0 sowieso nicht vorkommen:
- fkt_productoptions
- fkt_role
- fkt_tenant
- fkt_webshop

Die beiden Tabellen "databasechangelog" und "databasechangeloglock" dürfen nicht mit übernommen werden, da sie bereits durch Liquibase beim Anlegen der Datenbank befüllt werden.

Durch die Beschränkungen der Community-Edition mußte der Graph auf mehrere Dateien aufgeteilt werden. Außerdem konnten die Metadaten und die Verbindungen nicht ausgelagert werden.]]></attr>
</RichTextNote>
<Dictionary/>
</Global>
<Phase number="0">
<Node dbConnection="JDBC1" enabled="enabled" guiName="DBExecute" guiX="49" guiY="27" id="DB_EXECUTE0" type="DB_EXECUTE" url="${PROJECT}/cleanup_targetdb.sql"/>
</Phase>
<Phase number="1">
<Node dbConnection="JDBC0" guiName="Category" guiX="49" guiY="118" id="CATEGORY" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_CATEGORY]]></attr>
</Node>
<Node dbConnection="JDBC1" dbTable="fkt_category" fieldMap="$ID:=ID;$CATEGORYNAME:=CATEGORYNAME;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$ABSTRACTCATEGORY_PARENT:=ABSTRACTCATEGORY_PARENT;" guiName="DBOutputTable" guiX="403" guiY="118" id="DBOUTPUT_TABLE" type="DB_OUTPUT_TABLE"/>
<Edge fromNode="CATEGORY:0" guiBendpoints="" guiRouter="Manhattan" id="Edge0" inPort="Port 0 (in)" metadata="Metadata1" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE:0"/>
</Phase>
<Phase number="2">
<Node dbConnection="JDBC1" dbTable="fkt_vat" fieldMap="$ID:=ID;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$DESCRIPTION:=DESCRIPTION;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$SALESEQUALIZATIONTAX:=SALESEQUALIZATIONTAX;$TAXVALUE:=TAXVALUE;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$FK_CATEGORY:=FK_CATEGORY;" guiName="DBOutputTable" guiX="403" guiY="198" id="DBOUTPUT_TABLE2" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="VAT" guiX="49" guiY="198" id="VAT" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_VAT]]></attr>
</Node>
<Edge fromNode="VAT:0" guiBendpoints="" guiRouter="Manhattan" id="Edge2" inPort="Port 0 (in)" metadata="Metadata2" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE2:0"/>
</Phase>
<Phase number="3">
<Node dbConnection="JDBC1" dbTable="fkt_product" fieldMap="$ID:=ID;$BLOCK1:=BLOCK1;$BLOCK2:=BLOCK2;$BLOCK3:=BLOCK3;$BLOCK4:=BLOCK4;$BLOCK5:=BLOCK5;$CDF01:=CDF01;$CDF02:=CDF02;$CDF03:=CDF03;$COSTPRICE:=COSTPRICE;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$DESCRIPTION:=DESCRIPTION;$GTIN:=GTIN;$ITEMNUMBER:=ITEMNUMBER;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$PICTURE:=PICTURE;$PRICE1:=PRICE1;$PRICE2:=PRICE2;$PRICE3:=PRICE3;$PRICE4:=PRICE4;$PRICE5:=PRICE5;$QUANTITY:=QUANTITY;$QUANTITYUNIT:=QUANTITYUNIT;$SELLINGUNIT:=SELLINGUNIT;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$WEBSHOPID:=WEBSHOPID;$WEIGHT:=WEIGHT;$FK_CATEGORY:=FK_CATEGORY;$FK_VAT:=FK_VAT;" guiName="DBOutputTable" guiX="403" guiY="278" id="DBOUTPUT_TABLE1" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="Product" guiX="49" guiY="278" id="PRODUCT" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_PRODUCT]]></attr>
</Node>
<Edge fromNode="PRODUCT:0" guiBendpoints="" guiRouter="Manhattan" id="Edge1" inPort="Port 0 (in)" metadata="Metadata0" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE1:0"/>
</Phase>
<Phase number="4">
<Node dbConnection="JDBC1" dbTable="fkt_itemaccounttype" fieldMap="$ID:=ID;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$T_VALUE:=T_VALUE;$ITEMACCOUNTTYPE_CATEGORY:=ITEMACCOUNTTYPE_CATEGORY;" guiName="DBOutputTable" guiX="403" guiY="357" id="DBOUTPUT_TABLE3" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="ITEMACCOUNTTYPE" guiX="49" guiY="357" id="ITEMACCOUNTTYPE" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_ITEMACCOUNTTYPE]]></attr>
</Node>
<Edge fromNode="ITEMACCOUNTTYPE:0" guiBendpoints="" guiRouter="Manhattan" id="Edge3" inPort="Port 0 (in)" metadata="Metadata4" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE3:0"/>
</Phase>
<Phase number="5">
<Node dbConnection="JDBC1" dbTable="fkt_productblockprice" fieldMap="$ID:=ID;$BLOCK:=BLOCK;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$PRICE:=PRICE;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$FK_PRODUCT:=FK_PRODUCT;" guiName="DBOutputTable" guiX="445" guiY="436" id="DBOUTPUT_TABLE12" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="productblockprice" guiX="91" guiY="436" id="PRODUCTBLOCKPRICE" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_PRODUCTBLOCKPRICE]]></attr>
</Node>
<Edge fromNode="PRODUCTBLOCKPRICE:0" guiBendpoints="" guiRouter="Manhattan" id="Edge12" inPort="Port 0 (in)" metadata="Metadata3" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE12:0"/>
</Phase>
<Phase number="6">
<Node dbConnection="JDBC0" guiName="Address" guiX="49" guiY="514" id="ADDRESS" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_ADDRESS]]></attr>
</Node>
<Node dbConnection="JDBC1" dbTable="fkt_address" fieldMap="$ID:=ID;$CITY:=CITY;$CITYADDON:=CITYADDON;$COUNTRYCODE:=COUNTRYCODE;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$MANUALADDRESS:=MANUALADDRESS;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$STREET:=STREET;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$ZIP:=ZIP;" guiName="DBOutputTable" guiX="403" guiY="514" id="DBOUTPUT_TABLE11" type="DB_OUTPUT_TABLE"/>
<Edge fromNode="ADDRESS:0" guiBendpoints="" guiRouter="Manhattan" id="Edge11" inPort="Port 0 (in)" metadata="Metadata5" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE11:0"/>
</Phase>
<Phase number="7">
<Node dbConnection="JDBC0" guiName="Bankaccount" guiX="49" guiY="594" id="BANKACCOUNT" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_BANKACCOUNT]]></attr>
</Node>
<Node dbConnection="JDBC1" dbTable="fkt_bankaccount" fieldMap="$ID:=ID;$ACCOUNTHOLDER:=ACCOUNTHOLDER;$BANKCODE:=BANKCODE;$BANKNAME:=BANKNAME;$BIC:=BIC;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$IBAN:=IBAN;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;" guiName="DBOutputTable" guiX="403" guiY="594" id="DBOUTPUT_TABLE10" type="DB_OUTPUT_TABLE"/>
<Edge fromNode="BANKACCOUNT:0" guiBendpoints="" guiRouter="Manhattan" id="Edge10" inPort="Port 0 (in)" metadata="Metadata6" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE10:0"/>
</Phase>
<Phase number="8">
<Node dbConnection="JDBC0" guiName="CEFACTCODE" guiX="49" guiY="673" id="CEFACTCODE" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_CEFACTCODE]]></attr>
</Node>
<Node dbConnection="JDBC1" dbTable="fkt_cefactcode" fieldMap="$ID:=ID;$ABBREVIATION_DE:=ABBREVIATION_DE;$ABBREVIATION_EN:=ABBREVIATION_EN;$CODE:=CODE;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$NAME_DE:=NAME_DE;$TARGET:=TARGET;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;" guiName="DBOutputTable" guiX="403" guiY="673" id="DBOUTPUT_TABLE4" type="DB_OUTPUT_TABLE"/>
<Edge fromNode="CEFACTCODE:0" guiBendpoints="" guiRouter="Manhattan" id="Edge20" inPort="Port 0 (in)" metadata="Metadata7" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE4:0"/>
</Phase>
<Phase number="9">
<Node dbConnection="JDBC1" dbTable="fkt_payment" fieldMap="$ID:=ID;$CODE:=CODE;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$DEPOSITTEXT:=DEPOSITTEXT;$DESCRIPTION:=DESCRIPTION;$DISCOUNTDAYS:=DISCOUNTDAYS;$DISCOUNTVALUE:=DISCOUNTVALUE;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$NETDAYS:=NETDAYS;$PAIDTEXT:=PAIDTEXT;$UNPAIDTEXT:=UNPAIDTEXT;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$FK_CATEGORY:=FK_CATEGORY;" guiName="DBOutputTable" guiX="403" guiY="758" id="DBOUTPUT_TABLE5" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="PAYMENT" guiX="49" guiY="758" id="PAYMENT" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_PAYMENT]]></attr>
</Node>
<Edge fromNode="PAYMENT:0" guiBendpoints="" guiRouter="Manhattan" id="Edge21" inPort="Port 0 (in)" metadata="Metadata9" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE5:0"/>
</Phase>
</Graph>
