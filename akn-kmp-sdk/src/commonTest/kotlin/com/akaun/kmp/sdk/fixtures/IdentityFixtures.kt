package com.akaun.kmp.sdk.fixtures

// REAL: staging responses (api.akaun.cloud, 2026-11-10), personal data replaced with fakes, trimmed to a few items.
// HAND-BUILT: shaped from the backend Java classes, for endpoints that can't be called safely (they log in or send emails).

// GET core2/platform/dm/applets/user-applet-links
const val USER_APPLET_LINKS_JSON = """
{
  "code": "OK_RESPONSE",
  "message": "",
  "data": [
    {
      "guid": "c891ecc6-2424-42ea-8b3d-c3cb56560aa3",
      "user_applet_rank": "ADMIN",
      "applet_catalog_link_guid": "e241f8a9-040f-5df4-bc4a-1dda53b71558",
      "user_applet_index": 0,
      "status": "ACTIVE",
      "applet": {
        "guid": "7ea6aefe-793a-40fd-8094-2dc2943cca5b",
        "applet_mf_html_tag": "<stock-take-elements-XXXXXXXX></stock-take-elements-XXXXXXXX>",
        "applet_code": "stockTakeAppletV2",
        "applet_shortcode": "",
        "applet_icon_guid": "651a2220-cca4-4027-862f-741ac98b54ae",
        "applet_name": "Stock Take V2",
        "applet_description": "",
        "es_module_url": "https://staging-my-akaun-applets.s3.ap-southeast-5.amazonaws.com/bigledger/wavelet-erp/stock-take-applet/staging/stock-take-applet-elements.js",
        "routerlink": "applets/wavelet/erp/stock-take-applet",
        "store_guid": "5fa5ef75-3b4d-40a3-94e6-445cd7d9d2da",
        "status": "ACTIVE"
      },
      "catalog": {
        "guid": "fdd8406d-b428-47fd-a6bc-6a0de1b5a75d",
        "catalog_code": "Developer_Catalog",
        "catalog_name": "Developer_Catalog",
        "status": "ACTIVE"
      },
      "tenant": {
        "guid": "b94d011d-bebe-4a44-8e3e-f047974fb1b4",
        "tenant_code": "staging_tenant",
        "tenant_name": "staging_tenant",
        "status": "ACTIVE"
      },
      "store": {
        "guid": "5fa5ef75-3b4d-40a3-94e6-445cd7d9d2da",
        "code": "DEFAULT_STORE",
        "name": "Default Store"
      },
      "isStore": false,
      "notification_count": 0
    },
    {
      "guid": "fbd9a1b1-393e-4d8f-8beb-59f9bce2e1e3",
      "user_applet_rank": "ADMIN",
      "applet_catalog_link_guid": "109c1301-bb2c-a8c9-0a0c-ea6a780049e4",
      "user_applet_index": 0,
      "status": "ACTIVE",
      "applet": {
        "guid": "0920ab5b-8b2c-42df-bc49-82aa75ae2a81",
        "applet_mf_html_tag": "<cashbook-applet-elements-XXXXXXXX></cashbook-applet-elements-XXXXXXXX>",
        "applet_code": "cashbookApplet",
        "applet_shortcode": "",
        "applet_icon_guid": "ca1bc4f7-557a-4379-901c-dcc03c4a07ac",
        "applet_name": "Cashbook",
        "applet_description": "This applet is used to configure and manage the Cashbook and the Settlement method. For example, add an updated settlement method to the cash book which is configured with tax, charges and payment methods. This applet also can help the users to record the error of the settlement method (payment provider error). There are two modules in the applet which are Cash book, and settlement Method.",
        "es_module_url": "https://staging-my-akaun-applets.s3.ap-southeast-5.amazonaws.com/bigledger/wavelet-erp/cashbook-applet/staging/cashbook-applet-elements.js",
        "routerlink": "applets/wavelet/erp/finance/cashbook",
        "store_guid": "5fa5ef75-3b4d-40a3-94e6-445cd7d9d2da",
        "status": "ACTIVE"
      },
      "catalog": {
        "guid": "fdd8406d-b428-47fd-a6bc-6a0de1b5a75d",
        "catalog_code": "Developer_Catalog",
        "catalog_name": "Developer_Catalog",
        "status": "ACTIVE"
      },
      "tenant": {
        "guid": "b94d011d-bebe-4a44-8e3e-f047974fb1b4",
        "tenant_code": "staging_tenant",
        "tenant_name": "staging_tenant",
        "status": "ACTIVE"
      },
      "store": {
        "guid": "5fa5ef75-3b4d-40a3-94e6-445cd7d9d2da",
        "code": "DEFAULT_STORE",
        "name": "Default Store"
      },
      "isStore": false,
      "notification_count": 0
    }
  ]
}
"""

// GET core2/platform/dm/identity/userprofile
const val USER_PROFILE_JSON = """
{
  "code": "OK_RESPONSE",
  "data": {
    "appLoginSubject": {
      "guid": "7066aeec-cfab-40d3-afaf-f5c8a328dac5",
      "contact_key_guid": null,
      "name": "Test User",
      "name_first": "Test",
      "name_last": "User",
      "description": "Test User",
      "category": "PERSON",
      "account_type": null,
      "property_json": {
        "loginContactNumber": "redacted"
      },
      "revision": "10FE1DFF-CA92-4C07-8BA4-E59C3861AC29",
      "vrsn": null,
      "rank": null,
      "status": "ACTIVE",
      "created_date": "2026-01-16T08:02:19.173265Z",
      "updated_date": "2026-11-10T07:06:14.946248Z",
      "main_email": "test.user@example.com",
      "main_phone": "+60120000000",
      "dob": null,
      "gender": null,
      "login_subject_type": "PLATFORM",
      "mfa_enforced": null,
      "mfa_status": null,
      "app_login_mfa_guid": null,
      "validation_type": null,
      "default_language_code": null,
      "sidebar_enabled": true,
      "menu_set_hdr_guid": null
    },
    "appLoginSubjectExtList": [
      {
        "guid": "50DCC9C6-7A44-47A4-9F96-0410BCCB838E",
        "login_subject_guid": "7066aeec-cfab-40d3-afaf-f5c8a328dac5",
        "ext_type": null,
        "ext_code": null,
        "ext_option": null,
        "property_json": null,
        "param_code": "BIGLEDGER_APPLET_STORE_TNC",
        "param_name": "BIGLEDGER_APPLET_STORE_TNC",
        "param_type": "BOOLEAN",
        "param_option_json": null,
        "value_string": null,
        "value_file": null,
        "value_numeric": 1.0,
        "value_datetime": null,
        "value_json": null,
        "created_date": null,
        "updated_date": "2026-11-10T07:06:14.946262Z",
        "status": "ACTIVE",
        "revision": "10FE1DFF-CA92-4C07-8BA4-E59C3861AC29",
        "vrsn": null
      }
    ],
    "appLoginPrincipalList": [
      {
        "guid": "af75ab6a-98b1-4c98-b987-2dc3bf9610e2",
        "subject_guid": "7066aeec-cfab-40d3-afaf-f5c8a328dac5",
        "principal_type": "EMAIL_USERNAME",
        "principal_id": "test.user@example.com",
        "principal_value": "redacted",
        "credential": null,
        "valid_date_start": "2026-01-16T08:02:19.173316Z",
        "valid_date_end": "9000-12-31T00:00:00Z",
        "status": "USER_CONFIRMED",
        "revision": "A0FC3E1B-1C28-4600-8CB9-B0BB1E3CC07E",
        "vrsn": null,
        "created_date": "2026-01-16T08:02:19.173317Z",
        "updated_date": "2026-10-07T09:29:25.326451Z"
      }
    ]
  },
  "message": ""
}
"""

// HAND-BUILT from IdentityAuthenticationController.generateApiResponseWOAppletTenantTokenList + dto/identity/LoginResponse.java
// (POST core2/platform/dm/identity/login/no-applet-loading). Lists are always sent (empty), email/mobileNumber are "" when unused.
const val LOGIN_NO_APPLET_LOADING_JSON = """
{"code":"OK_RESPONSE","message":"","data":{
  "authToken":"eyJ.fake.token",
  "bl_fi_mst_entity_login_subject_link":[],
  "appletTenantTokenList":[],
  "subjectGuid":"7066aeec-cfab-40d3-afaf-f5c8a328dac5",
  "email":"test.user@example.com",
  "mobileNumber":""
}}
"""

// HAND-BUILT: same endpoint when tenantCode is given and the password has expired (ApiResponse.withoutData).
const val LOGIN_PASSWORD_EXPIRED_JSON = """
{"code":"CLIENT_AUTH_USER_NOT_AUTHORIZED","message":"Duration of when password was last changed until now has exceeded configured expiry duration","data":null}
"""

// HAND-BUILT from dal/table/app_login_registration.java: property_json / post_registration_json are Map columns -> JSON objects.
const val APP_LOGIN_REGISTRATION_JSON = """
{"code":"OK_RESPONSE","message":"","data":{"app_login_registration":{
  "guid":"0b0f6c1e-5a52-4d7e-9c55-1f2b3c4d5e6f",
  "email":"test.user@example.com",
  "registration_status":"PENDING",
  "property_json":{"source":"mobile"},
  "post_registration_json":{},
  "user_agreement_consent_json":{"tnc":true},
  "status":"ACTIVE",
  "created_date":"2026-11-10T07:06:14.946248Z"
}}}
"""
