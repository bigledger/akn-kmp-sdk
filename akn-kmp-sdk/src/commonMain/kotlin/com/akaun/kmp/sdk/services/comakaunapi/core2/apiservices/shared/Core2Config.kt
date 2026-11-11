package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared

class Core2Config {
    companion object {
        const val PG_PRFIX = ""

        const val DOMAIN_URL = "https://api.akaun.com/"
        const val CLOUD_URL = "https://api.akaun.cloud/"
        const val DEV_URL = "https://api.akaun.dev/"
        const val DOMAIN_TEST_URL = "https://api-test.akaun.com/"
        const val DOMAIN_TEST_URL_CORE2 = "https://api-test.akaun.com/core2/dm/"
        const val LOCAL_HOST = "http://localhost:5000/"

        const val DOMAIN_WEB_URL  = "https://akaun.com/"
        const val CLOUD_WEB_URL = "https://akaun.cloud/"
        const val DEV_WEB_URL = "https://akaun.dev/"

        const val PLATFORM_DOMAIN_URL_PREFIX = "core2/platform/dm/"

        const val CUSTOM_CELMONZE_URL_PREFIX = "cm/celmonze/"
        const val PLATFORM_URL_PREFIX = "core2/platform/"
        const val TENANT_URL_PREFIX = "core2/tnt/"
        const val DOMAIN_URL_PREFIX = "core2/dm/"

        const val PLATFORM_MICRO_SERVICES_URL_PREFIX = "core2/platform/ms/"

        const val ITEM_2_ITEM = "core2/t2t/dm/"

        const val TENANT_ECOM_URL_PREFIX = "core2/tnt/dm/ecom/"
        const val TENANT_DOMAIN_URL_PREFIX = "core2/tnt/dm/"
        const val ERP_MODULE_PREFIX = "erp/"
        const val TENANT_CUSTOM_URL_PREFIX = "core2/tnt/cm/"
        const val TENANT_DOMAIN_CRM_MODULE_URL_PREFIX = "core2/tnt/dm/crm/"
        const val TENANT_MICRO_SERVICES_CRM_URL_PREFIX = "core2/tnt/ms/crm/"

        const val T2T_DOMAIN_URL_PREFIX = "core2/t2t/dm/"

        const val DOMAIN_PREFIX = "dm/"
        const val MICRO_SERVICES_PREFIX = "ms/"
        const val CUSTOM_PREFIX = "cm/"
        const val PGW_PREFIX = "pgw/"
        const val ERP_PREFIX = "erp/"
        const val GEN_DOC_PREFIX = "gen-doc/"
        const val AAT_PREFIX = "aat/"
        const val CLAIMS_PREFIX = "claims/"
        const val CONFIG_PREFIX = "config/"
        const val CRM_PREFIX = "crm/"
        const val DIGI_PREFIX = "digi/"
        const val ENGAGEMENT_PREFIX = "engagements/"
        const val SENHENG_PREFIX = "senheng/"
        const val SENWAVE_PREFIX = "senwave/"
        const val UNIPIN_PREFIX = "unipin/"
        const val ECOMSYNC_PREFIX = "ecomsync/"
        const val CMS_PREFIX = "cms/"
        const val DGMKT_MODULE_PREFIX = "dgmkt/"
        const val INV_PREFIX = "inv/"
        const val INV_MODULE_PREFIX = "inv/"
        const val GENERIC_PREFIX = "gen-doc/"
        const val GENERIC_DOC_PREFIX = "generic-doc/"
        const val PRINT_PREFIX = "prt/"
        const val SCH_PREFIX = "sch/"
        const val WF_PREFIX = "wf/"
        const val ALG_CC_MODULE = "alg/cc/"
        const val ALG_BOT_MODULE = "alg/bot/"
        const val ALG_MODULE = "alg/"
        const val FM_MODULE = "fm/"
        const val CONTACT_KEY_PREFIX = "contact-key/"

        const val IDENTITY_MODULE_PREFIX = "identity/"
        const val FI_PREFIX = "fi/"
        const val APPLET_PREFIX = "applet/"
        const val COMMISSION_PREFIX = "commission/"
        const val CPCOMMERCE_PREFIX = "cp-commerce/"
        const val MADE_TO_ORDER_PREFIX = "mto/"

        const val STOCKTAKE_PREFIX = "stocktake/"

        const val SFA_MODULE_PREFIX = "sfa/"
        const val SALES_COMMISSION_PREFIX = "sales-commission-report/"
        const val REPORTS_PREFIX = "reports/"

        const val DATALIB_PREFIX = "datalib/"

        const val RECURRING_PREFIX = "recurring-docs/"
        const val TEMPLATES_PREFIX = "templates/"
        const val TPY_PREFIX = "tpy/"
        const val INNOVATIF_EKYC_PREFIX = "innovatif-ekyc/"
        const val WARRANTY_PREFIX = "wrty/"
        const val BACKOFFICE_EP_SUFFIX = "backoffice-ep"
        const val LOGIN_ENTITY_EP_SUFFIX = "login-entity-ep"
        const val LOGIN_EP_SUFFIX = "login-ep"
        const val MKP_PREFIX = "mkp/"
        const val HR_PREFIX = "hr/"
        const val HEALTHCARE_PREFIX = "hc/"
        const val MRP_PREFIX = "mrp/"
    }

    fun getWebUrl(url: String): String {
        return when (url) {
            DOMAIN_URL -> DOMAIN_WEB_URL
            CLOUD_URL -> CLOUD_WEB_URL
            else -> DEV_WEB_URL
        }
    }
}