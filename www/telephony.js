/**
 * JavaScript interface to abstract
 * the usage of the cordova Sygic Navigation plugin.
 *
 * @module com/dff/cordova/plugins/sygic
 */

'use strict';

var cordova = require('cordova');
var feature = "Telephony";
var self = {};

/**
 * Get call log.
 *
 */
self.getCallsLog = function (success, error, args) {
    cordova.exec(success, error, feature, "calllog", [args]);
};

self.call = function (success, error, args) {
    cordova.exec(success, error, feature, "call", [args]);
};

self.onCallStateChanged = function (success, error) {
    cordova.exec(success, error, feature, "onCallStateChanged", []);
};

self.onLog = function (success, error) {
    cordova.exec(success, error, feature, "onLog", []);
};

self.telephonyinfo = function (success, error) {
    cordova.exec(success, error, feature, "telephonyinfo", []);
}; 

module.exports = self;