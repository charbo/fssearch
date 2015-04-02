angular.module('ng-cloak', ['zeroclipboard']).
  config(['uiZeroclipConfigProvider', function(uiZeroclipConfigProvider) {

    // config ZeroClipboard
    uiZeroclipConfigProvider.setZcConf({
      swfPath: '../vendor/zeroclipboard/dist/ZeroClipboard.swf'
    });

  }]);
