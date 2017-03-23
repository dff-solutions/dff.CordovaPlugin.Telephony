# dff.CordovaPlugin.Telephony

Cordova plugin to access telephony functionality of Android

## Supported Platforms
  * Android

## Installation

```sh
$ cordova plugin add git@github.com:dff-solutions/dff.CordovaPlugin.Telephony.git
```

## Usage
Feature is available via global `Telephony`.

### Actions

#### getCallsLog

```js
/**
 * Retrieve calls log of devices.
 *
 * @name getCallsLog
 * @param success Success callback
 * @param error Error callback
 */
Telephony
    .getCallsLog(function () {
        console.log('success');
    }, function (reason) {
        console.error(reason);
    });
```

#### clearCalllog

```js
/**
 * Clears calls log of device.
 *
 * @name clearCalllog
 * @param success Success callback
 * @param error Error callback
 * @param args {Object} Named parameters.
 * @param args.where {String} SQL WHERE filter.
 * @param args.selectionArgs {String[]} Args for WHERE clause.
 */
Telephony
    .clearCalllog(function () {
        console.log('success');
    }, function (reason) {
        console.error(reason);
    });
```

#### call

```js
/**
 * Calls given number.
 *
 * @name call
 * @param success Success callback
 * @param error Error callback
 * @param args {Object} Named parameters.
 * @param args.number {String} Number to be called.
 */
Telephony
    .call(function () {
        console.log('success');
    }, function (reason) {
        console.error(reason);
    }, {
        number: '+49 123456789'
    });
```

#### onCallStateChanged

```js
/**
 * Listen to call state changes.
 *
 * @name onCallStateChanged
 * @param success Success callback
 * @param error Error callback
 */
Telephony
    .onCallStateChanged(function (callstate) {
        console.log(callstate);
    }, function (reason) {
        console.error(reason);
    });
```

#### telephonyinfo

```js
/**
 * Get informatino about telephony on device.
 *
 * @name telephonyinfo
 * @param success Success callback
 * @param error Error callback
 */
Telephony
    .telephonyinfo(function (info) {
        console.log(info);
    }, function (reason) {
        console.error(reason);
    });
```

## Documentation
- <a href="https://dff-solutions.github.io/dff.CordovaPlugin.Telephony/" target="_blank" >JAVA DOC</a>
