/**
 * JavaScript interface to abstract
 * the usage of the cordova Sygic Navigation plugin.
 *
 * @module com/dff/cordova/plugins/sygic
 */

'use strict';

var cordova = require('cordova');
var feature = "Telephony";
function Telephony() {};

var actions = [
    "calllog",
    "clearCalllog",
    "call",
    "onCallStateChanged",
    "telephonyinfo",
];

function createActionFunction (action) {
    return function (success, error, args) {
        args = args || {};
        cordova.exec(success, error, feature, action, [args]);
    }
}

actions.forEach(function (action) {
    Telephony.prototype[action] = createActionFunction(action);
});

module.exports = new Telephony();
