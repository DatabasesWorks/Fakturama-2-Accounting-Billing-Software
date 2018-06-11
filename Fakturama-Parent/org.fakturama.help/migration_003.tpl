<?xml version="1.0" encoding="UTF-8"?>
<Graph author="Ralf" created="Sat Jan 20 21:59:55 CET 2018" guiVersion="4.8.0" id="1516484859780" licenseCode="RegCode" name="migration_002" showComponentDetails="true">
<Global>
<Metadata id="Metadata8">
<Record fieldDelimiter=";" name="CONFIRMATION_METADATA" previewAttachmentCharset="UTF-8" recordDelimiter="\n" type="delimited">
<Field name="CONFIRMATION_PARENT_ID" size="64" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata0">
<Record fieldDelimiter=";" name="fkt_credit" recordDelimiter="\n" type="delimited">
<Field name="CREDIT_PARENT_ID" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata1">
<Record fieldDelimiter=";" name="fkt_dunning" recordDelimiter="\n" type="delimited">
<Field name="DUNNING_PARENT_ID" size="20" type="long"/>
<Field name="DUNNINGLEVEL" size="11" type="integer"/>
</Record>
</Metadata>
<Metadata id="Metadata2">
<Record fieldDelimiter=";" name="fkt_letter" recordDelimiter="\n" type="delimited">
<Field name="LETTER_PARENT_ID" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata3">
<Record fieldDelimiter=";" name="fkt_offer" recordDelimiter="\n" type="delimited">
<Field name="OFFER_PARENT_ID" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata4">
<Record fieldDelimiter=";" name="fkt_order" recordDelimiter="\n" type="delimited">
<Field name="ORDER_PARENT_ID" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata5">
<Record fieldDelimiter=";" name="fkt_proforma" recordDelimiter="\n" type="delimited">
<Field name="PROFORMA_PARENT_ID" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata7">
<Record fieldDelimiter=";" name="fkt_shipping" recordDelimiter="\n" type="delimited">
<Field name="ID" size="20" type="long"/>
<Field name="AUTOVAT" size="255" type="string"/>
<Field name="CODE" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" size="1" type="boolean"/>
<Field name="DESCRIPTION" size="715827882" type="string"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="SHIPPINGVALUE" size="22" type="number"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="FK_CATEGORY" size="20" type="long"/>
<Field name="FK_VAT" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata6">
<Record fieldDelimiter=";" name="fkt_textmodule" recordDelimiter="\n" type="delimited">
<Field name="ID" size="20" type="long"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" size="1" type="boolean"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="T_TEXT" size="715827882" type="string"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="FK_CATEGORY" size="20" type="long"/>
</Record>
</Metadata>
<Connection database="MYSQL" dbURL="jdbc:mysql://localhost/fktest" id="JDBC1" jdbcSpecific="MYSQL" name="FktMysql" password="fktestuser" type="JDBC" user="fktestuser"/>
<Connection database="HSQLDB" dbURL="jdbc:hsqldb:hsql://localhost/Fakturama2" id="JDBC0" jdbcSpecific="HSQLDB" name="hsqldb" type="JDBC" user="sa"/>
<GraphParameters>
<GraphParameterFile fileURL="workspace.prm"/>
</GraphParameters>
<Dictionary/>
</Global>
<Phase number="1">
<Node dbConnection="JDBC0" guiName="Confirmation" guiX="20" guiY="20" id="CONFIRMATION1" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_CONFIRMATION]]></attr>
</Node>
<Node dbConnection="JDBC1" dbTable="fkt_confirmation" fieldMap="$CONFIRMATION_PARENT_ID:=CONFIRMATION_PARENT_ID;" guiName="DBOutputTable" guiX="245" guiY="20" id="DBOUTPUT_TABLE" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_credit" fieldMap="$CREDIT_PARENT_ID:=CREDIT_PARENT_ID;" guiName="DBOutputTable" guiX="245" guiY="132" id="DBOUTPUT_TABLE1" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_dunning" fieldMap="$DUNNING_PARENT_ID:=DUNNING_PARENT_ID;$DUNNINGLEVEL:=DUNNINGLEVEL;" guiName="DBOutputTable" guiX="245" guiY="244" id="DBOUTPUT_TABLE2" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_letter" fieldMap="$LETTER_PARENT_ID:=LETTER_PARENT_ID;" guiName="DBOutputTable" guiX="245" guiY="356" id="DBOUTPUT_TABLE3" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_offer" fieldMap="$OFFER_PARENT_ID:=OFFER_PARENT_ID;" guiName="DBOutputTable" guiX="245" guiY="468" id="DBOUTPUT_TABLE4" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_order" fieldMap="$ORDER_PARENT_ID:=ORDER_PARENT_ID;" guiName="DBOutputTable" guiX="245" guiY="580" id="DBOUTPUT_TABLE5" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_proforma" fieldMap="$PROFORMA_PARENT_ID:=PROFORMA_PARENT_ID;" guiName="DBOutputTable" guiX="245" guiY="692" id="DBOUTPUT_TABLE7" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_textmodule" fieldMap="$ID:=ID;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$T_TEXT:=T_TEXT;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$FK_CATEGORY:=FK_CATEGORY;" guiName="DBOutputTable" guiX="245" guiY="804" id="DBOUTPUT_TABLE8" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_shipping" fieldMap="$ID:=ID;$AUTOVAT:=AUTOVAT;$CODE:=CODE;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$DESCRIPTION:=DESCRIPTION;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$SHIPPINGVALUE:=SHIPPINGVALUE;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$FK_CATEGORY:=FK_CATEGORY;$FK_VAT:=FK_VAT;" guiName="DBOutputTable" guiX="245" guiY="916" id="DBOUTPUT_TABLE9" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="FKT_CREDIT" guiX="20" guiY="132" id="FKT_CREDIT" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_CREDIT]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="FKT_DUNNING" guiX="20" guiY="244" id="FKT_DUNNING" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_DUNNING]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="FKT_LETTER" guiX="20" guiY="356" id="FKT_LETTER" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_LETTER]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="FKT_OFFER" guiX="20" guiY="468" id="FKT_OFFER" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_OFFER]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="FKT_ORDER" guiX="20" guiY="580" id="FKT_ORDER" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_ORDER]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="FKT_PROFORMA" guiX="20" guiY="692" id="FKT_PROFORMA" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_PROFORMA]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="FKT_SHIPPING" guiX="20" guiY="916" id="FKT_SHIPPING" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_SHIPPING]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="FKT_TEXTMODULE" guiX="20" guiY="804" id="FKT_TEXTMODULE" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_TEXTMODULE]]></attr>
</Node>
<Edge fromNode="CONFIRMATION1:0" guiBendpoints="" guiRouter="Manhattan" id="Edge0" inPort="Port 0 (in)" metadata="Metadata8" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE:0"/>
<Edge fromNode="FKT_CREDIT:0" guiBendpoints="" guiRouter="Manhattan" id="Edge1" inPort="Port 0 (in)" metadata="Metadata0" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE1:0"/>
<Edge fromNode="FKT_DUNNING:0" guiBendpoints="" guiRouter="Manhattan" id="Edge2" inPort="Port 0 (in)" metadata="Metadata1" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE2:0"/>
<Edge fromNode="FKT_LETTER:0" guiBendpoints="" guiRouter="Manhattan" id="Edge3" inPort="Port 0 (in)" metadata="Metadata2" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE3:0"/>
<Edge fromNode="FKT_OFFER:0" guiBendpoints="" guiRouter="Manhattan" id="Edge4" inPort="Port 0 (in)" metadata="Metadata3" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE4:0"/>
<Edge fromNode="FKT_ORDER:0" guiBendpoints="" guiRouter="Manhattan" id="Edge5" inPort="Port 0 (in)" metadata="Metadata4" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE5:0"/>
<Edge fromNode="FKT_PROFORMA:0" guiBendpoints="" guiRouter="Manhattan" id="Edge6" inPort="Port 0 (in)" metadata="Metadata5" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE7:0"/>
<Edge fromNode="FKT_SHIPPING:0" guiBendpoints="" guiRouter="Manhattan" id="Edge8" inPort="Port 0 (in)" metadata="Metadata7" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE9:0"/>
<Edge fromNode="FKT_TEXTMODULE:0" guiBendpoints="" guiRouter="Manhattan" id="Edge7" inPort="Port 0 (in)" metadata="Metadata6" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE8:0"/>
</Phase>
<Phase number="2">
<Node dbConnection="JDBC1" enabled="enabled" guiName="DBExecute" guiX="508" guiY="864" id="DB_EXECUTE0" type="DB_EXECUTE" url="${PROJECT}/enable_fk.sql"/>
</Phase>
</Graph>
