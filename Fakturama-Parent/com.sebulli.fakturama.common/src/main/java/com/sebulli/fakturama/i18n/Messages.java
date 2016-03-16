/**
 * 
 */
package com.sebulli.fakturama.i18n;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import org.eclipse.e4.core.services.nls.Message;

/**
 * This class contains all the message keys from
 * OSGI-INF/l10n/bundle*.properties for a convenient access to translated
 * strings.
 * 
 */


/*
 * Tip: Suche mit \.(\w)
 * Ersetze durch \U\1
 */


@Message(contributionURI = "platform:/plugin/com.sebulli.fakturama.rcp")
public class Messages {
	public String aboutText;
	public String Accountsettingspage159;
	public String Accountsettingspage160;
	public String Accountsettingspage161;
	public String applicationName;
	public String commandBrowserCommand;
	public String commandBrowserName;
	public String commandBrowserOpenStartpage;
	public String commandBrowserOpenUrllabel;
	public String commandBrowserTooltip;
	public String commandCalculatorName;
	public String commandCalculatorTooltip;
	public String commandContactsName;
	public String commandContactsTooltip;
	public String commandCreditorsName;
	public String commandCreditorsTooltip;
	public String commandDataName;
	public String commandDataNameTooltip;
	public String commandDebtorsName;
	public String commandDebtorsTooltip;
	public String commandDocumentsMoveDownName;
	public String commandDocumentsMoveDownTooltip;
	public String commandDocumentsMoveUpName;
	public String commandDocumentsMoveUpTooltip;
	public String commandDocumentsName;
	public String commandDocumentsTooltip;
	public String commandExpenditurevouchersName;
	public String commandExpenditurevouchersTooltip;
	public String commandListsName;
	public String commandListsTooltip;
	public String commandMarkorderCompleted;
	public String commandMarkorderWarnStockzero;
	public String commandNavigationImport;
	public String commandNavigationImportTooltip;
	public String commandNavigationMisc;
	public String commandNewContactTooltip;
	public String commandNewCreditorName;
	public String commandNewCreditorTooltip;
	public String commandNewDebtorName;
	public String commandNewDebtorTooltip;
	public String commandNewExpenditurevoucherTooltip;
	public String commandNewProductName;
	public String commandNewProductTooltip;
	public String commandNewReceiptvoucherTooltip;
	public String commandNewTooltip;
	public String commandOpenContactTooltip;
	public String commandOpenParcelName;
	public String commandOpenWwwName;
	public String commandParcelserviceTooltip;
	public String commandParcelserviceName;
	public String commandPaymentsName;
	public String commandPaymentsTooltip;
	public String commandProductsName;
	public String commandProductsTooltip;
	public String commandReceiptvouchersName;
	public String commandReceiptvouchersTooltip;
	public String commandReorganizeDocumentsMessage;
	public String commandReorganizeDocumentsUpdateMessage;
	public String commandSelectoldworkspaceName;
	public String commandSelectworkspaceName;
	public String commandSelectworkspaceTooltip;
	public String commandShippingsName;
	public String commandShippingsTooltip;
	public String commandTextsName;
	public String commandTextsTooltip;
	public String commandVatsName;
	public String commandVatsTooltip;
	public String commandWebshopName;
	public String commandWebshopTooltip;
	public String commonButtonSavechanges;
	public String commonButtonSavechangesquestion;
	public String commonButtonSetdefault;
	public String commonFieldAccount;
	public String commonFieldAccountholder;
	public String commonFieldAccountTooltip;
	public String commonFieldBalance;
	public String commonFieldCategory;
	public String commonFieldCity;
	public String commonFieldCompany;
	public String commonFieldCountry;
	public String commonFieldDate;
	public String commonFieldDeliveryaddress;
	public String commonFieldDescription;
	public String commonFieldDiscount;
	public String commonFieldDiscountDays;
	public String commonFieldDocument;
	public String commonFieldFirstname;
	public String commonFieldGender;
	public String commonFieldLastname;
	public String commonFieldName;
	public String commonFieldNetDays;
	public String commonFieldNumber;
	public String commonFieldOptional;
	public String commonFieldPicture;
	public String commonFieldPrice;
	public String commonFieldPrinted;
	public String commonFieldQuantity;
	public String commonFieldQuantityShort;
	public String commonFieldSalutation;
	public String commonFieldState;
	public String commonFieldStreet;
	public String commonFieldText;
	public String commonFieldTitle;
	public String commonFieldTotal;
	public String commonFieldUnitprice;
	public String commonFieldValue;
	public String commonFieldVat;
	public String commonFieldZipcode;
	public String commonLabelDefault;
	public String commonLabelSearchfield;
	public String configWorkspaceTemplatesName;
	public String contactCreditorFieldName;
	public String contactDebtorFieldName;
	public String contactFieldGln;
	public String contactFieldMrName;
	public String contactFieldMsName;
	public String contactFieldReliabilityGoodName;
	public String contactFieldReliabilityMediumName;
	public String contactFieldReliabilityPoorName;
	public String Data653;
	public String dataDefaultContactFormatExcludedcountries;
	public String dataDefaultContactFormatSalutation;
	public String dataDefaultContactFormatSalutationMen;
	public String dataDefaultContactFormatSalutationWomen;
	public String dataDefaultPayment;
	public String dataDefaultPaymentDescription;
	public String dataDefaultPaymentPaidtext;
	public String dataDefaultPaymentUnpaidtext;
	public String dataDefaultShipping;
	public String dataDefaultShippingDescription;
	public String dataDefaultVat;
	public String dataDefaultVatDescription;
	public String dataExpenditureItems;
	public String dataListAccountnumbers;
	public String dataReceiptvoucherItems;
	public String dataVatPurchasetax;
	public String dataVatSalestax;
	public String dialogAboutTitle;
	public String dialogCustomerStatisticsPart1;
	public String dialogDeletedatasetMessage;
	public String dialogDeletedatasetTitle;
	
	public String dialogItemdescriptionHeader;
	public String dialogItemdescriptionHint;
	
	public String dialogMessageboxTitleInfo;
	public String dialogMessageboxTitleWarning;
	public String dialogMessageboxTitleError;
	public String dialogOrderstatusFieldNotify;
	public String dialogOrderstatusTitle;
	public String dialogPrintooDocumentalreadycreated;
	public String dialogPrintooNotemplate;
	public String dialogProductPicturePreview;
	public String dialogSelectaddressTitle;
	public String dialogSelectaddressTooltip;
	public String dialogSelectdeliverynotesTitle;
	public String dialogSelectproductTitle;
	public String dialogSelectproductTooltip;
	public String dialogSelecttextTitle;
	public String documentDeliveryStateHasinvoice;
	public String documentDeliveryStateHasnoinvoice;
	public String documentDunningStatemarkerName;
	public String documentOrderStateClosed;
	public String documentOrderStateInprogress;
	public String documentOrderStateNotshipped;
	public String documentOrderStateOpen;
	public String documentOrderStatePaid;
	public String documentOrderStateShipped;
	public String documentOrderStateUnpaid;
	public String documentTypeConfirmationPlural;
	public String documentTypeCredititemsPlural;
	public String documentTypeDeliverynotePlural;
	public String documentTypeDunningPlural;
	public String documentTypeInvoicePlural;
	public String documentTypeLetterPlural;
	public String documentTypeOfferPlural;
	public String documentTypeOrderPlural;
	public String documentTypeProforma;
	public String documentTypeProformaPlural;
	public String editorBrowserButtonBack;
	public String editorBrowserButtonForward;
	public String editorBrowserButtonHome;
	public String editorBrowserButtonProject;
	public String editorBrowserButtonReload;
	public String editorBrowserButtonStop;
	public String editorContactErrorCustomerid;
	public String editorContactErrorCustomernumber;
	public String editorContactErrorNotnextfreenumber;
	public String editorContactFieldAccountnumberDisabledinfo;
	public String editorContactFieldAccountnumberName;
	public String editorContactFieldBankcodeDisabledinfo;
	public String editorContactFieldBankcodeName;
	public String editorContactFieldBankName;
	public String editorContactFieldBirthdayName;
	public String editorContactFieldBirthdayTooltip;
	public String editorContactFieldCategoryTooltip;
	public String editorContactFieldDeliverersbirthdayTooltip;
	public String editorContactFieldDeliveryaddressequalsName;
	public String editorContactFieldDiscountTooltip;
	public String editorContactFieldFirstnamelastnameName;
	public String editorContactFieldLastnamefirstnameName;
	public String editorContactFieldMandaterefName;
	public String editorContactFieldNetgrossName;
	public String editorContactFieldNumberName;
	public String editorContactFieldNumberTooltip;
	public String editorContactFieldPaymentName;
	public String editorContactFieldPaymentTooltip;
	public String editorContactFieldReliabilityName;
	public String editorContactFieldSuppliernumberName;
	public String editorContactFieldZipcityName;
	public String editorContactHintSeepreferences;
	public String editorContactHintSethomecountry;
	public String editorContactLabelAddress;
	public String editorContactLabelBankaccount;
	public String editorContactLabelNotice;
	public String editorDocumentAdditemTooltip;
	public String editorDocumentCheckboxPaidTooltip;
	public String editorDocumentCollectiveinvoiceTooltip;
	public String editorDocumentCreateduplicate;
	public String editorDocumentDateofpayment;
	public String editorDocumentDateTooltip;
	public String editorDocumentDeleteitemTooltip;
	public String editorDocumentDialogGrossvalues;
	public String editorDocumentDialogNetvalues;
	public String editorDocumentDialogWarningDocumentexists;
	public String editorDocumentDiscountTooltip;
	public String editorDocumentDuedays;
	public String editorDocumentDuedaysTooltip;
	public String editorDocumentErrorDocnumberNotnextfree;
	public String editorDocumentErrorDocnumberTitle;
	public String editorDocumentErrorWrongcontactMsg1;
	public String editorDocumentErrorWrongcontactMsg2;
	public String editorDocumentErrorWrongcontactTitle;
	public String editorDocumentFieldCommentTooltip;
	public String editorDocumentFieldConsultant;
	public String editorDocumentFieldConsultantTooltip;
	public String editorDocumentFieldCustref;
	public String editorDocumentFieldCustrefTooltip;
	public String editorDocumentFieldDeposit;
	public String editorDocumentFieldInvoice;
	public String editorDocumentFieldInvoiceTooltip;
	public String editorDocumentFieldOrderdate;
	public String editorDocumentFieldOrderdateTooltip;
	public String editorDocumentFieldPosition;
	public String editorDocumentFieldQunit;
	public String editorDocumentFieldRemarks;
	public String editorDocumentFieldRemarksTooltip;
	public String editorDocumentFieldRequestdate;
	public String editorDocumentFieldRequestdateTooltip;
	public String editorDocumentFieldServicedate;
	public String editorDocumentFieldServicedateTooltip;
	public String editorDocumentFieldShipping;
	public String editorDocumentFieldShippingTooltip;
	public String editorDocumentItems;
	public String editorDocumentNetgrossTooltip;
	public String editorDocumentPaidvalue;
	public String editorDocumentPaidat;
	public String editorDocumentPayuntil;
	public String editorDocumentPayuntilTooltip;
	public String editorDocumentRefnumberTooltip;
	public String editorDocumentSelecttemplateTooltip;
	public String editorDocumentTotalgross;
	public String editorDocumentTotalnet;
	public String editorDocumentWarningDifferentaddress;
	public String editorDocumentWarningDifferentdeliveryaddress;
	public String editorDocumentZerovatTooltip;
	public String editorListHeader;
	public String editorListListfield;
	public String editorListTooltip;
	public String editorPaymentAccountTooltip;
	public String editorPaymentDefaultButtonHint;
	public String editorPaymentDefaultButtonName;
	public String editorPaymentDefaultTooltip;
	public String editorPaymentDepositName;
	public String editorPaymentDepositTooltip;
	public String editorPaymentDiscount;
	public String editorPaymentDiscountDays;
	public String editorPaymentDiscountDaysTooltip;
	public String editorPaymentDiscountTooltip;
	public String editorPaymentNameTooltip;
	public String editorPaymentNetdaysTooltip;
	public String editorPaymentPaidName;
	public String editorPaymentPaidTooltip;
	public String editorPaymentPlaceholderInfo;
	public String editorPaymentUnpaidName;
	public String editorPaymentUnpaidTooltip;
	public String editorProductAdddescriptionTooltip;
	public String editorProductButtonChoosepicName;
	public String editorProductCategoryTooltip;
	public String editorProductErrorItemnumberNotnextfree;
	public String editorProductErrorItemnumberTitle;
	public String editorProductFieldGrosspriceName;
	public String editorProductFieldGtin;
	public String editorProductFieldPriceName;
	public String editorProductFieldQuantityunitName;
	public String editorProductFieldQuantityunitNameShort;
	public String editorProductLabelFrom;
	public String editorProductNameTooltip;
	public String editorProductVatName;
	public String editorProductWarningDuplicatearticle;
	public String editorShippingCategoryTooltip;
	public String editorShippingDefaultButton;
	public String editorShippingDefaultButtonTooltip;
	public String editorShippingDefaultTooltip;
	public String editorShippingFieldAutovatcalculationName;
	public String editorShippingFieldAutovatcalculationTooltip;
	public String editorShippingFieldAutovatConstantName;
	public String editorShippingFieldAutovatFromvalueGross;
	public String editorShippingFieldAutovatFromvalueNet;
	public String editorShippingNameTooltip;
	public String editorShippingTitle;
	public String editorTextHeader;
	public String editorTextNameNeu;
	public String editorVatCategoryTooltip;
	public String editorVatDefaultbutton;
	public String editorVatDefaultbuttonTooltip;
	public String editorVatDefaultTooltip;
	public String editorVatDescriptionTooltip;
	public String editorVatHeader;
	public String editorVatNameTooltip;
	public String editorVatTitle;
	public String editorVoucherHeader;
	public String expenditurevoucherEditorTitle;
	public String expenditurevoucherFieldSupplier;
	public String exporterDataAccounttype;
	public String exporterDataBic;
	public String exporterDataBuyers;
	public String exporterDataDocno;
	public String exporterDataEarnings;
	public String exporterDataEmail;
	public String exporterDataIban;
	public String exporterDataInvoicedate;
	public String exporterDataInvoiceno;
	public String exporterDataInvoicesUnpaid;
	public String exporterDataInvoiceValue;
	public String exporterDataItemnumber;
	public String exporterDataMobile;
	public String exporterDataNetval;
	public String exporterDataOptions;
	public String exporterDataPayday;
	public String exporterDataPayvalue;
	public String exporterDataPicture;
	public String exporterDataProduct;
	public String exporterDataRebate;
	public String exporterDataTelefax;
	public String exporterDataTelephone;
	public String exporterDataUnit;
	public String exporterDataVatid;
	public String exporterDataVatno;
	public String exporterDataVatnoValid;
	public String exporterDataVolume;
	public String exporterDataVoucher;
	public String exporterDataWebsite;
	public String exporterDataWeight;
	public String wizardExportCommonFilterlabel;
	public String wizardExportCommonDescription;
	public String wizardExportCommonHeadline;
	public String wizardExportCommonTitle;
	public String Importer130;
	public String Importer131;
	public String Importer132;
	public String Importer133;
	public String Importer134;
	public String Importer135;
	public String Importer136;
	public String Importer137;
	public String Importer138;
	public String Importer140;
	public String Importer141;
	public String Importer142;
	public String Importer143;
	public String Importer144;
	public String Importer145;
	public String Importer146;
	public String Importoptionpage123;
	public String Importoptionpage124;
	public String Importoptionpage125;
	public String Importprogressdialog147;
	public String importWebshopActionError;
	public String importWebshopActionLabel;
	public String importWebshopDataCashondelivery;
	public String importWebshopDataCheque;
	public String importWebshopDataCreditcard;
	public String importWebshopDataPrepayment;
	public String importWebshopErrorCantconnect;
	public String importWebshopErrorCantconvertnumber;
	public String importWebshopErrorCantopen;
	public String importWebshopErrorCantopenpicture;
	public String importWebshopErrorCantread;
	public String importWebshopErrorMalformedurl;
	public String importWebshopErrorNodata;
	public String importWebshopErrorTotalsumcheckit;
	public String importWebshopErrorTotalsumincorrect;
	public String importWebshopErrorUrlnotset;
	public String importWebshopInfoConnected;
	public String importWebshopInfoConnection;
	public String importWebshopInfoImportorders;
	public String importWebshopInfoLoading;
	public String importWebshopInfoLoadingpicture;
	public String importWebshopInfoSuccess;
	public String importWebshopInfoTotalsum;
	public String importWebshopInfoWebshopno;
	public String Importwizard126;
	public String Importwizard127;
	public String Importwizard128;
	public String Importwizard129;
	public String Importwizard139;
	public String Installaction94;
	public String introRoot;
	public String mainMenuData;
	public String mainMenuDataTooltip;
	public String mainMenuEdit;
	public String mainMenuEditDeleteName;
	public String mainMenuEditDeleteTooltip;
	public String mainMenuEditMarkaspaid;
	public String mainMenuEditMarkaspending;
	public String mainMenuEditMarkasprocessing;
	public String mainMenuEditMarkasshipped;
	public String mainMenuEditMarkasunpaid;
	public String mainMenuEditRemoveinvoicereference;
	public String mainMenuExtra;
	public String mainMenuExtraCalculator;
	public String mainMenuExtraCalculatorTooltip;
	public String mainMenuExtraReorganize;
	public String mainMenuFile;
	public String mainMenuFileClose;
	public String mainMenuFileCloseall;
	public String mainMenuFileExit;
	public String mainMenuFileExitQuestion;
	public String mainMenuFileExport;
	public String mainMenuFileImport;
	public String mainMenuFileOpenpreferences;
	public String mainMenuFilePrint;
	public String mainMenuFilePrintTooltip;
	public String mainMenuFilePrintTooltipDeprecated;
	public String mainMenuFileSave;
	public String mainMenuFileSaveall;
	public String mainMenuHelp;
	public String mainMenuHelpAbout;
	public String mainMenuHelpContents;
	public String mainMenuHelpDynamic;
	public String mainMenuHelpInstall;
	public String mainMenuHelpIntro;
	public String mainMenuHelpReset;
	public String mainMenuHelpSearch;
	public String mainMenuNew;
	public String mainMenuNewConfirmation;
	public String mainMenuNewContactName;
	public String mainMenuNewCredit;
	public String mainMenuNewDeliverynote;
	public String mainMenuNewDunning;
	public String mainMenuNewExpenditurevoucher;
	public String mainMenuNewInvoice;
	public String mainMenuNewLetter;
	public String mainMenuNewListentry;
	public String mainMenuNewListentryTooltip;
	public String mainMenuNewOffer;
	public String mainMenuNewOrder;
	public String mainMenuNewPayment;
	public String mainMenuNewPaymentTooltip;
	public String mainMenuNewProforma;
	public String mainMenuNewReceiptvoucher;
	public String mainMenuNewShipping;
	public String mainMenuNewShippingTooltip;
	public String mainMenuNewText;
	public String mainMenuNewTextTooltip;
	public String mainMenuNewTooltip;
	public String mainMenuNewVat;
	public String mainMenuNewVatTooltip;
	public String mainMenuWindow;
	public String officePathInvalid;
	public String officePathName;
	public String Openparcelserviceaction110;
	public String pageBrowser;
	public String pageColumnwidth;
	public String pageColumnwidthcontacts;
	public String pageColumnwidthdialogcontacts;
	public String pageColumnwidthdialogproducts;
	public String pageColumnwidthdialogtexts;
	public String pageColumnwidthdocuments;
	public String pageColumnwidthitems;
	public String pageColumnwidthlist;
	public String pageColumnwidthpayments;
	public String pageColumnwidthproducts;
	public String pageColumnwidthshippings;
	public String pageColumnwidthtexts;
	public String pageColumnwidthvat;
	public String pageColumnwidthvoucheritems;
	public String pageColumnwidthvouchers;
	public String pageCompany;
	public String pageCompanyBankaccount;
	public String pageCompanyBankname;
	public String pageCompanyCreditorid;
	public String pageCompanyName;
	public String pageCompanyOwner;
	public String pageCompanyStreet;
	public String pageCompanyTaxoffice;
	public String pageCompanyVatno;
	public String pageContacts;
	public String pageContactsFormat;
	public String pageExport;
	public String pageGeneral;
	public String pageNumberrange;
	public String pageOpenoffice;
	public String pageOptionalitems;
	public String pageParcelservice;
	public String pageProducts;
	public String pageToolbar;
	public String pageWebshop;
	public String pageWebshopauthorization;
	public String pageWebshopsettings;
	public String Parcelserviceformfiller648;
	public String Parcelserviceformfiller649;
	public String Parcelserviceformfiller650;
	public String parcelServiceName;
	public String partdescDocview;
	public String pathsDocumentsName;
	public String preferencesBrowserShowaddressbar;
	public String preferencesBrowserType;
	public String preferencesBrowserUrl;
	public String preferencesContact;
	public String preferencesContactFirstlastname;
	public String preferencesContactFormatAddressfield;
	public String preferencesContactFormatAddressfieldlabel;
	public String preferencesContactFormatHidecountries;
	public String preferencesContactFormatSalutation;
	public String preferencesContactFormatSalutationCompany;
	public String preferencesContactFormatSalutationMen;
	public String preferencesContactFormatSalutationWomen;
	public String preferencesContactLastfirstname;
	public String preferencesContactNameformat;
	public String preferencesContactUsebankaccount;
	public String preferencesContactUsecompany;
	public String preferencesContactUsecountry;
	public String preferencesContactUsedelivery;
	public String preferencesContactUsegender;
	public String preferencesContactUsemiscpage;
	public String preferencesContactUsenotepage;
	public String preferencesContactUsetitle;
	public String preferencesDocument;
	public String preferencesDocumentAdddelnotenumber;
	public String preferencesDocumentAlsoaddress;
	public String preferencesDocumentCopydescfield;
	public String preferencesDocumentCopymsgfield;
	public String preferencesDocumentDisplaypreview;
	public String preferencesDocumentLabelCompare;
	public String preferencesDocumentLabelDepositrow;
	public String preferencesDocumentLabelFinalrow;
	public String preferencesDocumentNumberofremarkfields;
	public String preferencesDocumentOnlycontactid;
	public String preferencesDocumentShowcustomerstat;
	public String preferencesDocumentShowitemsprices;
	public String preferencesDocumentUsediscountall;
	public String preferencesDocumentUsediscountsingle;
	public String preferencesDocumentUsenetgross;
	public String preferencesDocumentUsepos;
	public String preferencesExport;
	public String preferencesExportUsepaydate;
	public String preferencesGeneral;
	public String preferencesGeneralCloseeditors;
	public String preferencesGeneralCollapsenavbar;
	public String preferencesGeneralCurrency;
	public String preferencesGeneralCurrencyCashrounding;
	public String preferencesGeneralCurrencyCashroundingTooltip;
	public String preferencesGeneralCurrencyExample;
	public String preferencesGeneralCurrencyLocale;
	public String preferencesGeneralCurrencyUsesymbol;
	public String preferencesGeneralThousandseparator;
	public String preferencesNumberrangeFormatConfirmationoLabel;
	public String preferencesNumberrangeFormatConfirmationoValue;
	public String preferencesNumberrangeFormatCreditnoLabel;
	public String preferencesNumberrangeFormatCreditnoValue;
	public String preferencesNumberrangeFormatDebitornoLabel;
	public String preferencesNumberrangeFormatDebitornoValue;
	public String preferencesNumberrangeFormatCreditornoLabel;
	public String preferencesNumberrangeFormatCreditornoValue;
	public String preferencesNumberrangeFormatDeliverynotenoLabel;
	public String preferencesNumberrangeFormatDeliverynotenoValue;
	public String preferencesNumberrangeFormatDescription;
	public String preferencesNumberrangeFormatDunningnoLabel;
	public String preferencesNumberrangeFormatDunningnoValue;
	public String preferencesNumberrangeFormatInvoicenoLabel;
	public String preferencesNumberrangeFormatInvoicenoValue;
	public String preferencesNumberrangeFormatItemnoLabel;
	public String preferencesNumberrangeFormatOffernoLabel;
	public String preferencesNumberrangeFormatOffernoValue;
	public String preferencesNumberrangeFormatOrdernoLabel;
	public String preferencesNumberrangeFormatOrdernoValue;
	public String preferencesNumberrangeFormatProformanoLabel;
	public String preferencesNumberrangeFormatProformanoValue;
	public String preferencesNumberrangeValuesLabel;
	public String preferencesNumberrangeValuesLabelNextconfirmno;
	public String preferencesNumberrangeValuesLabelNextcreditno;
	public String preferencesNumberrangeValuesLabelNextdeliverynoteno;
	public String preferencesNumberrangeValuesLabelNextdunningno;
	public String preferencesNumberrangeValuesLabelNextinvoiceno;
	public String preferencesNumberrangeValuesLabelNextitemno;
	public String preferencesNumberrangeValuesLabelNextdebitorno;
	public String preferencesNumberrangeValuesLabelNextcreditorno;
	public String preferencesNumberrangeValuesLabelNextofferno;
	public String preferencesNumberrangeValuesLabelNextorderno;
	public String preferencesNumberrangeValuesLabelNextproformano;
	public String preferencesOffice;
	public String preferencesOfficeApp;
	public String preferencesOfficeAppfieldNovalidapp;
	public String preferencesOfficeAppfieldNovalidfolder;
	public String preferencesOfficeExampleshort;
	public String preferencesOfficeExportasLabel;
	public String preferencesOfficeFolder;
	public String preferencesOfficeFormatandpathodt;
	public String preferencesOfficeFormatandpathpdf;
	public String preferencesOfficeOdtpdfLabel;
	public String preferencesOfficeOnlyodtLabel;
	public String preferencesOfficeOnlypdfLabel;
	public String preferencesOfficeStartnewthread;
	public String preferencesOptionalitems;
	public String preferencesOptionalitemsItemlabel;
	public String preferencesOptionalitemsLabel;
	public String preferencesOptionalitemsPricereplacement;
	public String preferencesOptionalitemsReplaceprice;
	public String preferencesOptionalitemsUse;
	public String preferencesProduct;
	public String preferencesProductNetandgross;
	public String preferencesProductNetorgrossprices;
	public String preferencesProductScaledprices;
	public String preferencesProductSelectvat;
	public String preferencesProductUsedescription;
	public String preferencesProductUseitemno;
	public String preferencesProductUsepicture;
	public String preferencesProductUsequantity;
	public String preferencesProductUsequantityunit;
	public String preferencesProductUseweight;
	public String preferencesToolbarIcons;
	public String preferencesToolbarShowicon;
	public String preferencesWebshop;
	public String preferencesWebshopAuthorization;
	public String preferencesWebshopAuthorizationPasswordproteced;
	public String preferencesWebshopCustomer;
	public String preferencesWebshopDefaulturl;
	public String preferencesWebshopEanasitemno;
	public String preferencesWebshopEnabled;
	public String preferencesWebshopImport;
	public String preferencesWebshopLabelCustomersincategory;
	public String preferencesWebshopLabelProductsincategory;
	public String preferencesWebshopLabelShippingsincategory;
	public String preferencesWebshopMaxproducts;
	public String preferencesWebshopModifiedproducts;
	public String preferencesWebshopNotifycustomerOnprogress;
	public String preferencesWebshopNotifycustomerOnshipped;
	public String preferencesWebshopSettings;
	public String preferencesWebshopUrl;
	public String preferencesWebshopUser;
	public String preferencesWebshopSettingsAssignstate;
	public String preferencesWebshopSettingsAssignstateMissing;
	public String preferencesWebshopSettingsDescription;
	public String preferencesWebshopSettingsGetallstates;
	public String preferencesWebshopSettingsShopinfo;
	public String preferencesWebshopSettingsStateError;
	public String preferencesWebshopSettingsStateFakturama;
	public String preferencesWebshopSettingsStateWebshop;
	public String preferencesWebshopSettingsVersion;
	
	public String productDataGross;
	public String productDataNet;
	public String productFieldItemno;
	public String Receiptvouchereditor642;
	public String receiptvoucherFieldBook;
	public String receiptvoucherFieldCustomer;
	public String receiptvoucherFieldVoucher;
	public String dialogReorganizeQuestion;
	public String dialogReorganizeDonemessage;
	public String startFirstRestartmessage;
	public String startFirstSelectDbCredentialsJdbc;
	public String startFirstSelectDbCredentialsName;
	public String startFirstSelectDbCredentialsPassword;
	public String startFirstSelectDbCredentialsUser;
	public String startFirstSelectDbUsedefault;
	public String startFirstSelectDbUsedefaultTooltip;
	public String startFirstSelectOldworkdirShort;
	public String startFirstSelectOldworkdirVerbose;
	public String startFirstSelectWorkdir;
	public String startFirstSelectWorkdirShort;
	public String startFirstSelectWorkdirNoselection;
	public String startFirstSelectWorkdirVerbose;
	public String startFirstTitle;
	public String startMigration;
	public String startMigrationBegin;
	public String startMigrationConvert;
	public String startMigrationEnd;
	public String startMigrationWorking;
	public String textFieldCategoryTooltip;
	public String textFieldNameTooltip;
	public String textFieldTextTooltip;
	public String todo2_digit_country_code;
	public String todo3_digit_country_code;
	public String todoCreate_collective_in;
	public String todoDocument_icon;
	public String todoDunning_no;
	public String todoError_closing_a_file;
	public String todoImporting_country_co;
	public String todoIn_work;
	public String todoIs_invalid;
	public String todoNew_document;
	public String todoSave_changes;
	public String todoSubject;
	public String todoThe_subject_of_this_;
	public String toolbarNewConfirmationName;
	public String toolbarNewConfirmationNameLong;
	public String toolbarNewContactName;
	public String toolbarNewCreditName;
	public String toolbarNewDeliveryName;
	public String toolbarNewDeliverynoteName;
	public String toolbarNewDocumentCreditName;
	public String toolbarNewDocumentDunningName;
	public String toolbarNewExpenditurevoucherName;
	public String toolbarNewInvoiceName;
	public String toolbarNewLetterName;
	public String toolbarNewOfferName;
	public String toolbarNewOrderName;
	public String toolbarNewProductName;
	public String toolbarNewReceiptvoucherName;
	public String topictreeAll;
	public String topictreeAllCustomersTooltip;
	public String topictreeAllDocumentsTooltip;
	public String topictreeLabelThistransaction;
	public String topictreeTransaction;
	public String Updateaction111;
	public String viewErrorlogLabel;
	public String viewErrorlogName;
	public String voucherDialogBookConfirmHeader;
	public String voucherDialogBookConfirmWarning;
	public String voucherFieldBookName;
	public String voucherFieldBookTooltip;
	public String voucherFieldCustomersupplierName;
	public String voucherFieldDateTooltip;
	public String voucherFieldDocumentnumberName;
	public String voucherFieldDocumentnumberTooltip;
	public String voucherFieldItemsDeleteposTooltip;
	public String voucherFieldItemsName;
	public String voucherFieldItemsNewposTooltip;
	public String voucherFieldNumberName;
	public String voucherFieldNumberTooltip;
	public String voucherFieldPaidvalueName;
	public String voucherFieldPaidvalueTooltip;
	public String voucherFieldTotalvalueName;
	public String voucherFieldTotalvalueTooltip;
	public String voucherFieldWithdiscountName;
	public String voucherFieldWithdiscountTooltip;
	public String widgetNovatproviderWithvatLabel;
	public String wizardImportCsvContactsDescription;
	public String wizardImportCsvContactsName;
	public String wizardImportCsvExpendituresDescription;
	public String wizardImportCsvExpendituresName;
	public String wizardImportCsvProductsDescription;
	public String wizardImportCsvProductsName;

	public String getPurchaseTaxString() {
		// T: Name of the tax that is raised when goods are purchased
		return dataVatPurchasetax;
	}

	public String getSalesTaxString() {
		// T: Name of the tax that is raised when goods are sold
		return dataVatSalestax;
	}

	/**
	 * This method helps you to create messages from compound keys, i.e. if you
	 * try to use "some.key"+".tooltip". Don't use it for simple known keys
	 * because this method uses reflection for finding the appropriate string
	 * representation.
	 * 
	 * @param key
	 *            the key which has to be looked up
	 * @return message or (if not found) the key itself (enclosed in "!")
	 */
	public String getMessageFromKey(final String key) {
		if (key == null) {
			return "";
		}
		String retval = key.replaceAll("-", ".");
		// try to make the string representation of this key via reflection

		try {
			Class<?> c = this.getClass();
			// make the key java-like :-)
			StringBuffer sb = new StringBuffer();
			StrTokenizer st = new StrTokenizer(retval, ".");
			while (st.hasNext()) {
				if (st.previousIndex() == -1) {
					sb.append(st.next());
					continue;
				}
				sb.append(StringUtils.capitalize(st.next()));
			}

			Field chap = c.getDeclaredField(sb.toString());
			retval = (String) chap.get(this);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			retval = "?" + retval + "?";
		}
		return retval;
	}

}
