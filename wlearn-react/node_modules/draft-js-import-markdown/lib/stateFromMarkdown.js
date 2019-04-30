'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

exports.default = stateFromMarkdown;

var _MarkdownParser = require('./MarkdownParser');

var _MarkdownParser2 = _interopRequireDefault(_MarkdownParser);

var _draftJsImportElement = require('draft-js-import-element');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

var defaultOptions = {};

function stateFromMarkdown(markdown, options) {
  var _ref = options || defaultOptions,
      parserOptions = _ref.parserOptions,
      otherOptions = _objectWithoutProperties(_ref, ['parserOptions']);

  var element = _MarkdownParser2.default.parse(markdown, _extends({ getAST: true }, parserOptions));
  return (0, _draftJsImportElement.stateFromElement)(element, otherOptions);
}