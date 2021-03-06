<?xml version="1.0" encoding="UTF-8"?>
<Graph author="Ralf" created="Sat Jan 20 21:59:55 CET 2018" guiVersion="4.8.0" id="1516484859780" licenseCode="RegCode" name="migration_002" showComponentDetails="true">
<Global>
<Metadata id="Metadata10">
<Record fieldDelimiter=";" name="fkt_contact" previewAttachmentCharset="UTF-8" recordDelimiter="\n" type="delimited">
<Field name="ID" size="64" type="long"/>
<Field name="DTYPE" size="31" type="string"/>
<Field format="yyyy-MM-dd" name="BIRTHDAY" size="10" type="date"/>
<Field name="COMPANY" size="255" type="string"/>
<Field name="CONTACTTYPE" size="32" type="integer"/>
<Field name="CUSTOMERNUMBER" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" type="boolean"/>
<Field name="DISCOUNT" size="64" type="number"/>
<Field name="EMAIL" size="255" type="string"/>
<Field name="FAX" size="255" type="string"/>
<Field name="FIRSTNAME" size="255" type="string"/>
<Field name="GENDER" size="32" type="integer"/>
<Field name="GLN" size="64" type="long"/>
<Field name="MANDATEREFERENCE" size="255" type="string"/>
<Field name="MOBILE" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="NOTE" type="string"/>
<Field name="PHONE" size="255" type="string"/>
<Field name="RELIABILITY" size="255" type="string"/>
<Field name="SUPPLIERNUMBER" size="255" type="string"/>
<Field name="TITLE" size="255" type="string"/>
<Field name="USENETGROSS" size="16" type="integer"/>
<Field name="USESALESEQUALIZATIONTAX" type="boolean"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="VATNUMBER" size="255" type="string"/>
<Field name="VATNUMBERVALID" type="boolean"/>
<Field name="WEBSHOPNAME" size="255" type="string"/>
<Field name="WEBSITE" size="255" type="string"/>
<Field name="FK_ADDRESS" size="64" type="long"/>
<Field name="FK_ALTERNATECONTACT" size="64" type="long"/>
<Field name="FK_BANKACCOUNT" size="64" type="long"/>
<Field name="FK_CATEGORY" size="64" type="long"/>
<Field name="FK_PAYMENT" size="64" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata0">
<Record fieldDelimiter=";" name="fkt_delivery" recordDelimiter="\n" type="delimited">
<Field name="DELIVERY_PARENT_ID" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata5">
<Record fieldDelimiter=";" name="fkt_document" recordDelimiter="\n" type="delimited">
<Field name="ID" size="20" type="long"/>
<Field name="DTYPE" size="31" type="string"/>
<Field name="ADDRESSFIRSTLINE" size="255" type="string"/>
<Field name="BILLINGTYPE" size="11" type="integer"/>
<Field name="CONSULTANT" size="255" type="string"/>
<Field name="CUSTOMERREF" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" size="1" type="boolean"/>
<Field name="DEPOSIT" size="1" type="boolean"/>
<Field format="yyyy-MM-dd" name="DOCUMENTDATE" size="10" type="date"/>
<Field name="DUEDAYS" size="11" type="integer"/>
<Field name="ITEMSREBATE" size="22" type="number"/>
<Field name="MESSAGE" size="715827882" type="string"/>
<Field name="MESSAGE2" size="715827882" type="string"/>
<Field name="MESSAGE3" size="715827882" type="string"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="NETGROSS" size="11" type="integer"/>
<Field name="ODTPATH" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="ORDERDATE" size="10" type="date"/>
<Field name="PAID" size="1" type="boolean"/>
<Field name="PAIDVALUE" size="22" type="number"/>
<Field format="yyyy-MM-dd" name="PAYDATE" size="10" type="date"/>
<Field name="PDFPATH" size="255" type="string"/>
<Field name="PRINTTEMPLATE" size="255" type="string"/>
<Field name="PRINTED" size="1" type="boolean"/>
<Field name="PROGRESS" size="11" type="integer"/>
<Field format="yyyy-MM-dd" name="SERVICEDATE" size="10" type="date"/>
<Field name="SHIPPINGAUTOVAT" size="255" type="string"/>
<Field name="SHIPPINGVALUE" size="22" type="number"/>
<Field name="TOTALVALUE" size="22" type="number"/>
<Field name="TRANSACTIONID" size="11" type="integer"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VESTINGPERIODEND" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VESTINGPERIODSTART" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="WEBSHOPDATE" size="10" type="date"/>
<Field name="WEBSHOPID" size="255" type="string"/>
<Field name="FK_INDIVIDUALINFO" size="20" type="long"/>
<Field name="FK_CONTACT" size="20" type="long"/>
<Field name="FK_DELIVERYCONTACT" size="20" type="long"/>
<Field name="DOCUMENT_INVOICEREFERENCE" size="20" type="long"/>
<Field name="FK_NOVATREF" size="20" type="long"/>
<Field name="FK_PAYMENT" size="20" type="long"/>
<Field name="FK_SHIPPING" size="20" type="long"/>
<Field name="FK_SRCDOCUMENT" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata7">
<Record fieldDelimiter=";" name="fkt_documentitem" recordDelimiter="\n" type="delimited">
<Field name="ID" size="20" type="long"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" size="1" type="boolean"/>
<Field name="DESCRIPTION" size="715827882" type="string"/>
<Field name="GTIN" size="20" type="long"/>
<Field name="ITEMNUMBER" size="255" type="string"/>
<Field name="ITEMREBATE" size="22" type="number"/>
<Field name="ITEMTYPE" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="NOVAT" size="1" type="boolean"/>
<Field name="OPTIONAL" size="1" type="boolean"/>
<Field name="PICTURE" size="2147483647" type="byte"/>
<Field name="POSNR" size="11" type="integer"/>
<Field name="PRICE" size="22" type="number"/>
<Field name="QUANTITY" size="22" type="number"/>
<Field name="QUANTITYUNIT" size="255" type="string"/>
<Field name="TARA" size="22" type="number"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VESTINGPERIODEND" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VESTINGPERIODSTART" size="10" type="date"/>
<Field name="WEIGHT" size="22" type="number"/>
<Field name="FK_VAT" size="20" type="long"/>
<Field name="FK_PRODUCT" size="20" type="long"/>
<Field name="FK_DOCUMENT" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata6">
<Record fieldDelimiter=";" name="fkt_individualdocumentinfo" recordDelimiter="\n" type="delimited">
<Field name="ID" size="20" type="long"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" size="1" type="boolean"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="NOVATDESCRIPTION" size="2048" type="string"/>
<Field name="NOVATNAME" size="255" type="string"/>
<Field name="PAYMENTDESCRIPTION" size="2048" type="string"/>
<Field name="PAYMENTNAME" size="255" type="string"/>
<Field name="PAYMENTTEXT" size="4096" type="string"/>
<Field name="SHIPPINGAUTOVAT" size="11" type="integer"/>
<Field name="SHIPPINGDESCRIPTION" size="2048" type="string"/>
<Field name="SHIPPINGNAME" size="255" type="string"/>
<Field name="SHIPPINGVALUE" size="22" type="number"/>
<Field name="SHIPPINGVATDESCRIPTION" size="2048" type="string"/>
<Field name="SHIPPINGVATVALUE" size="22" type="number"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
</Record>
</Metadata>
<Metadata id="Metadata4">
<Record fieldDelimiter=";" name="fkt_invoice" recordDelimiter="\n" type="delimited">
<Field name="INVOICE_PARENT_ID" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata9">
<Record fieldDelimiter=";" name="fkt_userproperty" recordDelimiter="\n" type="delimited">
<Field name="ID" size="20" type="long"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="T_DEFAULT" size="255" type="string"/>
<Field name="DELETED" size="1" type="boolean"/>
<Field name="T_GLOBAL" size="1" type="boolean"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="T_USER" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="T_VALUE" size="255" type="string"/>
</Record>
</Metadata>
<Metadata id="Metadata3">
<Record fieldDelimiter=";" name="fkt_voucheritems" recordDelimiter="\n" type="delimited">
<Field name="ID" size="20" type="long"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" size="1" type="boolean"/>
<Field name="ITEMVOUCHERTYPE" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="POSNR" size="11" type="integer"/>
<Field name="PRICE" size="22" type="number"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="FK_ACCOUNTTYPE" size="20" type="long"/>
<Field name="FK_VAT" size="20" type="long"/>
<Field name="FK_VOUCHER" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata2">
<Record fieldDelimiter=";" name="fkt_vouchers" recordDelimiter="\n" type="delimited">
<Field name="ID" size="20" type="long"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" size="1" type="boolean"/>
<Field name="DISCOUNTED" size="1" type="boolean"/>
<Field name="DONOTBOOK" size="1" type="boolean"/>
<Field name="DOCUMENTNUMBER" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field name="PAIDVALUE" size="22" type="number"/>
<Field name="TOTALVALUE" size="22" type="number"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VOUCHERDATE" size="10" type="date"/>
<Field name="VOUCHERNUMBER" size="255" type="string"/>
<Field name="VOUCHERTYPE" size="255" type="string"/>
<Field name="FK_CATEGORY" size="20" type="long"/>
</Record>
</Metadata>
<Metadata id="Metadata1">
<Record fieldDelimiter=";" name="fkt_webshopstatemapping" recordDelimiter="\n" type="delimited">
<Field name="ID" size="20" type="long"/>
<Field format="yyyy-MM-dd" name="DATEADDED" size="10" type="date"/>
<Field name="DELETED" size="1" type="boolean"/>
<Field name="FAKTURAMAORDERSTATE" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="MODIFIED" size="10" type="date"/>
<Field name="MODIFIEDBY" size="255" type="string"/>
<Field name="NAME" size="255" type="string"/>
<Field format="yyyy-MM-dd" name="VALIDFROM" size="10" type="date"/>
<Field format="yyyy-MM-dd" name="VALIDTO" size="10" type="date"/>
<Field name="WEBSHOPSTATE" size="255" type="string"/>
<Field name="FK_WEBSHOP" size="20" type="long"/>
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
<Node dbConnection="JDBC0" guiName="Contact" guiX="20" guiY="20" id="CONTACT" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_CONTACT]]></attr>
</Node>
<Node dbConnection="JDBC1" dbTable="fkt_contact" fieldMap="$ID:=ID;$DTYPE:=DTYPE;$BIRTHDAY:=BIRTHDAY;$COMPANY:=COMPANY;$CONTACTTYPE:=CONTACTTYPE;$CUSTOMERNUMBER:=CUSTOMERNUMBER;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$DISCOUNT:=DISCOUNT;$EMAIL:=EMAIL;$FAX:=FAX;$FIRSTNAME:=FIRSTNAME;$GENDER:=GENDER;$GLN:=GLN;$MANDATEREFERENCE:=MANDATEREFERENCE;$MOBILE:=MOBILE;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$NOTE:=NOTE;$PHONE:=PHONE;$RELIABILITY:=RELIABILITY;$SUPPLIERNUMBER:=SUPPLIERNUMBER;$TITLE:=TITLE;$USENETGROSS:=USENETGROSS;$USESALESEQUALIZATIONTAX:=USESALESEQUALIZATIONTAX;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$VATNUMBER:=VATNUMBER;$VATNUMBERVALID:=VATNUMBERVALID;$WEBSHOPNAME:=WEBSHOPNAME;$WEBSITE:=WEBSITE;$FK_ADDRESS:=FK_ADDRESS;$FK_ALTERNATECONTACT:=FK_ALTERNATECONTACT;$FK_BANKACCOUNT:=FK_BANKACCOUNT;$FK_CATEGORY:=FK_CATEGORY;$FK_PAYMENT:=FK_PAYMENT;" guiName="DBOutputTable" guiX="245" guiY="20" id="DBOUTPUT_TABLE" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_webshopstatemapping" fieldMap="$ID:=ID;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$FAKTURAMAORDERSTATE:=FAKTURAMAORDERSTATE;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$WEBSHOPSTATE:=WEBSHOPSTATE;$FK_WEBSHOP:=FK_WEBSHOP;" guiName="DBOutputTable" guiX="245" guiY="132" id="DBOUTPUT_TABLE2" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_vouchers" fieldMap="$ID:=ID;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$DISCOUNTED:=DISCOUNTED;$DONOTBOOK:=DONOTBOOK;$DOCUMENTNUMBER:=DOCUMENTNUMBER;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$PAIDVALUE:=PAIDVALUE;$TOTALVALUE:=TOTALVALUE;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$VOUCHERDATE:=VOUCHERDATE;$VOUCHERNUMBER:=VOUCHERNUMBER;$VOUCHERTYPE:=VOUCHERTYPE;$FK_CATEGORY:=FK_CATEGORY;" guiName="DBOutputTable" guiX="245" guiY="244" id="DBOUTPUT_TABLE3" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_voucheritems" fieldMap="$ID:=ID;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$ITEMVOUCHERTYPE:=ITEMVOUCHERTYPE;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$POSNR:=POSNR;$PRICE:=PRICE;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$FK_ACCOUNTTYPE:=FK_ACCOUNTTYPE;$FK_VAT:=FK_VAT;$FK_VOUCHER:=FK_VOUCHER;" guiName="DBOutputTable" guiX="245" guiY="356" id="DBOUTPUT_TABLE4" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_userproperty" fieldMap="$ID:=ID;$DATEADDED:=DATEADDED;$T_DEFAULT:=T_DEFAULT;$DELETED:=DELETED;$T_GLOBAL:=T_GLOBAL;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$T_USER:=T_USER;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$T_VALUE:=T_VALUE;" guiName="DBOutputTable" guiX="677" guiY="969" id="DBOUTPUT_TABLE9" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="FKT_USERPROPERTY" guiX="452" guiY="969" id="FKT_USERPROPERTY" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_USERPROPERTY]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="fkt_voucheritems" guiX="20" guiY="356" id="FKT_VOUCHERITEMS" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from fkt_voucheritems]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="fkt_vouchers" guiX="20" guiY="244" id="FKT_VOUCHERS" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_VOUCHERS]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="fkt_webshopstatemapping" guiX="20" guiY="132" id="FKT_WEBSHOPSTATEMAPPING" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from fkt_webshopstatemapping]]></attr>
</Node>
<Edge fromNode="CONTACT:0" guiBendpoints="" guiRouter="Manhattan" id="Edge0" inPort="Port 0 (in)" metadata="Metadata10" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE:0"/>
<Edge fromNode="FKT_USERPROPERTY:0" guiBendpoints="" guiRouter="Manhattan" id="Edge30" inPort="Port 0 (in)" metadata="Metadata9" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE9:0"/>
<Edge fromNode="FKT_VOUCHERITEMS:0" guiBendpoints="" guiRouter="Manhattan" id="Edge14" inPort="Port 0 (in)" metadata="Metadata3" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE4:0"/>
<Edge fromNode="FKT_VOUCHERS:0" guiBendpoints="" guiRouter="Manhattan" id="Edge13" inPort="Port 0 (in)" metadata="Metadata2" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE3:0"/>
<Edge fromNode="FKT_WEBSHOPSTATEMAPPING:0" guiBendpoints="" guiRouter="Manhattan" id="Edge12" inPort="Port 0 (in)" metadata="Metadata1" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE2:0"/>
</Phase>
<Phase number="2">
<Node dbConnection="JDBC1" dbTable="fkt_individualdocumentinfo" fieldMap="$ID:=ID;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$NOVATDESCRIPTION:=NOVATDESCRIPTION;$NOVATNAME:=NOVATNAME;$PAYMENTDESCRIPTION:=PAYMENTDESCRIPTION;$PAYMENTNAME:=PAYMENTNAME;$PAYMENTTEXT:=PAYMENTTEXT;$SHIPPINGAUTOVAT:=SHIPPINGAUTOVAT;$SHIPPINGDESCRIPTION:=SHIPPINGDESCRIPTION;$SHIPPINGNAME:=SHIPPINGNAME;$SHIPPINGVALUE:=SHIPPINGVALUE;$SHIPPINGVATDESCRIPTION:=SHIPPINGVATDESCRIPTION;$SHIPPINGVATVALUE:=SHIPPINGVATVALUE;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;" guiName="DBOutputTable" guiX="245" guiY="468" id="DBOUTPUT_TABLE6" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="fkt_individualdocumentinfo" guiX="20" guiY="468" id="FKT_INDIVIDUALDOCUMENTINFO" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_INDIVIDUALDOCUMENTINFO]]></attr>
</Node>
<Edge fromNode="FKT_INDIVIDUALDOCUMENTINFO:0" guiBendpoints="" guiRouter="Manhattan" id="Edge16" inPort="Port 0 (in)" metadata="Metadata6" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE6:0"/>
</Phase>
<Phase number="3">
<Node dbConnection="JDBC1" dbTable="FKT_DOCUMENT" fieldMap="$ID:=ID;$DTYPE:=DTYPE;$ADDRESSFIRSTLINE:=ADDRESSFIRSTLINE;$BILLINGTYPE:=BILLINGTYPE;$CONSULTANT:=CONSULTANT;$CUSTOMERREF:=CUSTOMERREF;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$DEPOSIT:=DEPOSIT;$DOCUMENTDATE:=DOCUMENTDATE;$DUEDAYS:=DUEDAYS;$ITEMSREBATE:=ITEMSREBATE;$MESSAGE:=MESSAGE;$MESSAGE2:=MESSAGE2;$MESSAGE3:=MESSAGE3;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$NETGROSS:=NETGROSS;$ODTPATH:=ODTPATH;$ORDERDATE:=ORDERDATE;$PAID:=PAID;$PAIDVALUE:=PAIDVALUE;$PAYDATE:=PAYDATE;$PDFPATH:=PDFPATH;$PRINTTEMPLATE:=PRINTTEMPLATE;$PRINTED:=PRINTED;$PROGRESS:=PROGRESS;$SERVICEDATE:=SERVICEDATE;$SHIPPINGAUTOVAT:=SHIPPINGAUTOVAT;$SHIPPINGVALUE:=SHIPPINGVALUE;$TOTALVALUE:=TOTALVALUE;$TRANSACTIONID:=TRANSACTIONID;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$VESTINGPERIODEND:=VESTINGPERIODEND;$VESTINGPERIODSTART:=VESTINGPERIODSTART;$WEBSHOPDATE:=WEBSHOPDATE;$WEBSHOPID:=WEBSHOPID;$FK_INDIVIDUALINFO:=FK_INDIVIDUALINFO;$FK_CONTACT:=FK_CONTACT;$FK_DELIVERYCONTACT:=FK_DELIVERYCONTACT;$DOCUMENT_INVOICEREFERENCE:=DOCUMENT_INVOICEREFERENCE;$FK_NOVATREF:=FK_NOVATREF;$FK_PAYMENT:=FK_PAYMENT;$FK_SHIPPING:=FK_SHIPPING;$FK_SRCDOCUMENT:=FK_SRCDOCUMENT;" guiName="DBOutputTable" guiX="245" guiY="580" id="DBOUTPUT_TABLE7" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="FKT_DOCUMENT" guiX="20" guiY="580" id="FKT_DOCUMENT" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_DOCUMENT]]></attr>
</Node>
<Edge fromNode="FKT_DOCUMENT:0" guiBendpoints="" guiRouter="Manhattan" id="Edge17" inPort="Port 0 (in)" metadata="Metadata5" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE7:0"/>
</Phase>
<Phase number="4">
<Node dbConnection="JDBC1" dbTable="fkt_documentitem" fieldMap="$ID:=ID;$DATEADDED:=DATEADDED;$DELETED:=DELETED;$DESCRIPTION:=DESCRIPTION;$GTIN:=GTIN;$ITEMNUMBER:=ITEMNUMBER;$ITEMREBATE:=ITEMREBATE;$ITEMTYPE:=ITEMTYPE;$MODIFIED:=MODIFIED;$MODIFIEDBY:=MODIFIEDBY;$NAME:=NAME;$NOVAT:=NOVAT;$OPTIONAL:=OPTIONAL;$PICTURE:=PICTURE;$POSNR:=POSNR;$PRICE:=PRICE;$QUANTITY:=QUANTITY;$QUANTITYUNIT:=QUANTITYUNIT;$TARA:=TARA;$VALIDFROM:=VALIDFROM;$VALIDTO:=VALIDTO;$VESTINGPERIODEND:=VESTINGPERIODEND;$VESTINGPERIODSTART:=VESTINGPERIODSTART;$WEIGHT:=WEIGHT;$FK_VAT:=FK_VAT;$FK_PRODUCT:=FK_PRODUCT;$FK_DOCUMENT:=FK_DOCUMENT;" guiName="DBOutputTable" guiX="245" guiY="692" id="DBOUTPUT_TABLE8" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="fkt_documentitem" guiX="20" guiY="692" id="FKT_DOCUMENTITEM" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from FKT_DOCUMENTITEM]]></attr>
</Node>
<Edge fromNode="FKT_DOCUMENTITEM:0" guiBendpoints="" guiRouter="Manhattan" id="Edge18" inPort="Port 0 (in)" metadata="Metadata7" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE8:0"/>
</Phase>
<Phase number="5">
<Node dbConnection="JDBC1" dbTable="fkt_delivery" fieldMap="$DELIVERY_PARENT_ID:=DELIVERY_PARENT_ID;" guiName="DBOutputTable" guiX="245" guiY="804" id="DBOUTPUT_TABLE1" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC1" dbTable="fkt_invoice" fieldMap="$INVOICE_PARENT_ID:=INVOICE_PARENT_ID;" guiName="DBOutputTable" guiX="245" guiY="916" id="DBOUTPUT_TABLE5" type="DB_OUTPUT_TABLE"/>
<Node dbConnection="JDBC0" guiName="fkt_delivery" guiX="20" guiY="804" id="FKT_DELIVERY" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from fkt_delivery]]></attr>
</Node>
<Node dbConnection="JDBC0" guiName="fkt_invoice" guiX="20" guiY="916" id="FKT_INVOICE" type="DB_INPUT_TABLE">
<attr name="sqlQuery"><![CDATA[select * from fkt_invoice]]></attr>
</Node>
<Edge fromNode="FKT_DELIVERY:0" guiBendpoints="" guiRouter="Manhattan" id="Edge11" inPort="Port 0 (in)" metadata="Metadata0" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE1:0"/>
<Edge fromNode="FKT_INVOICE:0" guiBendpoints="" guiRouter="Manhattan" id="Edge15" inPort="Port 0 (in)" metadata="Metadata4" outPort="Port 0 (out)" toNode="DBOUTPUT_TABLE5:0"/>
</Phase>
</Graph>
