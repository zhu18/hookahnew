'use strict';

// Modules
var webpack = require('webpack');
var autoprefixer = require('autoprefixer');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var CopyWebpackPlugin = require('copy-webpack-plugin');
var glob = require('glob');


/**
 * Env
 * Get npm lifecycle event to identify the environment
 */
var ENV = process.env.npm_lifecycle_event;
var isTest = ENV === 'test' || ENV === 'test-watch';
var isProd = ENV === 'build';

console.log("isTest:" + isTest);
console.log("isProd:" + isProd);

/**
 * 动态查找所有入口文件
 */
var files = glob.sync('./src/apps/*/index.js');
var newEntries = {};

files.forEach(function (f) {
  // var name = /.*\/(apps\/.*?\/index)\.js/.exec(f)[1];//得到apps/question/index这样的文件名
  var name = /.*\/apps\/(.*?)\/index\.js/.exec(f)[1];
  var newName = name + '/' + name;
  newEntries[newName] = f;
});

module.exports = function makeWebpackConfig() {
  /**
   * Config
   * Reference: http://webpack.github.io/docs/configuration.html
   * This is the object where all configuration gets set
   */
  var config = {};

  /**
   * Entry
   * Reference: http://webpack.github.io/docs/configuration.html#entry
   * Should be an empty object if it's generating a test build
   * Karma will set this when it's a test build
   */
  config.entry = isTest ? {} : {
      vendor: ['jquery', 'bootstrap', 'angular', 'angular-ui-bootstrap', 'angular-ui-router', 'spin', 'angular-spinner', 'ng-file-upload'],
      uibootstrap: [ 'wangEditor', 'angular-growl-v2','angular-bootstrap-grid-tree']
    };
  config.entry = Object.assign({}, config.entry, newEntries);
  console.log(config.entry);
  config.resolve = {
    alias: {
      'jquery': __dirname + '/node_modules/jquery/dist/jquery.js',
      'bootstrap': __dirname + '/node_modules/bootstrap/dist/js/bootstrap.min.js',
      'bootstrapCss': __dirname + '/node_modules/bootstrap/dist/css/bootstrap.css',
      'wangEditor': __dirname + '/vendor/wangEditor/wangEditor.min.js',
      'wangEditorCss': __dirname + '/vendor/wangEditor/wangEditor.min.css',
      'angular-growl-v2': __dirname + '/vendor/angular-growl/build/angular-growl.js',
      'treeGridCss': __dirname + '/node_modules/angular-bootstrap-grid-tree',
      'spin': __dirname + '/node_modules/spin.js/spin.js',
    }
  };

  /**
   * Output
   * Reference: http://webpack.github.io/docs/configuration.html#output
   * Should be an empty object if it's generating a test build
   * Karma will handle setting it up for you when it's a test build
   */
  config.output = isTest ? {} : {
      // Absolute output directory
      path: __dirname + '/dist',

      // Output path from the view of the page
      // Uses webpack-dev-server in development
      // publicPath: isProd ? '/' : 'http://localhost:9500/',
      publicPath: isProd ? '/' : 'http://admin.qddata.com.cn/',
      // Filename for entry points
      // Only adds hash in build mode
      filename: isProd ? '[name].[hash].js' : '[name].bundle.js',
      // filename: '[name].bundle.js',

      // Filename for non-entry points
      // Only adds hash in build mode
      chunkFilename: isProd ? '[name].[hash].js' : '[name].bundle.js'
      // chunkFilename: '[name].chunk.js'
    };

  /**
   * Devtool
   * Reference: http://webpack.github.io/docs/configuration.html#devtool
   * Type of sourcemap to use per build type
   */
  if (isTest) {
    config.devtool = 'inline-source-map';
  } else if (isProd) {
    config.devtool = 'source-map';
  } else {
    config.devtool = 'eval-source-map';
  }

  /**
   * Loaders
   * Reference: http://webpack.github.io/docs/configuration.html#module-loaders
   * List: http://webpack.github.io/docs/list-of-loaders.html
   * This handles most of the magic responsible for converting modules
   */

  // Initialize module
  config.module = {
    preLoaders: [],
    loaders: [{
      // JS LOADER
      // Reference: https://github.com/babel/babel-loader
      // Transpile .js files using babel-loader
      // Compiles ES6 and ES7 into ES5 code
      test: /\.js$/,
      loader: 'babel',
      exclude: /node_modules/
    }, {
      // CSS LOADER
      // Reference: https://github.com/webpack/css-loader
      // Allow loading css through js
      //
      // Reference: https://github.com/postcss/postcss-loader
      // Postprocess your css with PostCSS plugins
      test: /\.css$/,
      // Reference: https://github.com/webpack/extract-text-webpack-plugin
      // Extract css files in production builds
      //
      // Reference: https://github.com/webpack/style-loader
      // Use style-loader in development.
      loader: isTest ? 'null' : ExtractTextPlugin.extract('style-loader', 'css-loader?sourceMap!postcss-loader')
    }, {
      // ASSET LOADER
      // Reference: https://github.com/webpack/file-loader
      // Copy png, jpg, jpeg, gif, svg, woff, woff2, ttf, eot files to output
      // Rename the file using the asset hash
      // Pass along the updated reference to your code
      // You can add here any file extension you want to get copied to your output
      test: /\.(png|jpg|jpeg|gif|svg|woff|woff2|ttf|eot)$/,
      loader: 'file'
    }, {
      // HTML LOADER
      // Reference: https://github.com/webpack/raw-loader
      // Allow loading html through js
      test: /\.html$/,
      loader: 'raw'
    }
      // , {
      //   test: /\.woff(\?v=\d+\.\d+\.\d+)?$/,
      //   loader: "url?limit=10000&mimetype=application/font-woff"
      // }, {
      //   test: /\.woff2(\?v=\d+\.\d+\.\d+)?$/,
      //   loader: "url?limit=10000&mimetype=application/font-woff"
      // }, {
      //   test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
      //   loader: "url?limit=10000&mimetype=application/octet-stream"
      // }, {
      //   test: /\.eot(\?v=\d+\.\d+\.\d+)?$/,
      //   loader: "file"
      // }, {
      //   test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
      //   loader: "url?limit=10000&mimetype=image/svg+xml"
      // }
    ]
  };

  // ISTANBUL LOADER
  // https://github.com/deepsweet/istanbul-instrumenter-loader
  // Instrument JS files with istanbul-lib-instrument for subsequent code coverage reporting
  // Skips node_modules and files that end with .test
  if (isTest) {
    config.module.preLoaders.push({
      test: /\.js$/,
      exclude: [
        /node_modules/,
        /\.spec\.js$/
      ],
      loader: 'istanbul-instrumenter',
      query: {
        esModules: true
      }
    })
  }

  /**
   * PostCSS
   * Reference: https://github.com/postcss/autoprefixer-core
   * Add vendor prefixes to your css
   */
  config.postcss = [
    autoprefixer({
      browsers: ['last 2 version']
    })
  ];

  /**
   * Plugins
   * Reference: http://webpack.github.io/docs/configuration.html#plugins
   * List: http://webpack.github.io/docs/list-of-plugins.html
   */
  config.plugins = [
    new webpack.ProvidePlugin({
      jQuery: 'jquery',
      $: 'jquery',
      jquery: 'jquery',
      spin: 'spin',
    })
  ];

  // Skip rendering index.html in test mode
  if (!isTest) {
    // Reference: https://github.com/ampedandwired/html-webpack-plugin
    // Render index.html
    files.forEach(function (f) {
      // var name = /.*\/(apps\/.*?\/index)\.js/.exec(f)[1];//得到apps/question/index这样的文件名
      var name = /.*\/apps\/(.*?)\/index\.js/.exec(f)[1];
      var nname = name + '/' + name;
      config.plugins.push(
        new HtmlWebpackPlugin({
          filename: name + '/index.html',
          template: './src/public/index.html',
          inject: 'body',
          favicon: './src/style/images/favicon.ico',
          chunks: [nname, 'uibootstrap', 'vendor']
        })
      )
    });
    config.plugins.push(
      // Reference: https://github.com/webpack/extract-text-webpack-plugin
      // Extract css files
      // Disabled when in test mode or not in build mode
      new ExtractTextPlugin('[name].[hash].css', {disable: !isProd})
    );
  }

  // Add build specific plugins
  if (isProd) {
    config.plugins.push(
      // Reference: http://webpack.github.io/docs/list-of-plugins.html#noerrorsplugin
      // Only emit files when there are no errors
      new webpack.NoErrorsPlugin(),

      // Reference: http://webpack.github.io/docs/list-of-plugins.html#dedupeplugin
      // Dedupe modules in the output
      new webpack.optimize.DedupePlugin(),

      // Reference: http://webpack.github.io/docs/list-of-plugins.html#uglifyjsplugin
      // Minify all javascript, switch loaders to minimizing mode
      new webpack.optimize.UglifyJsPlugin(),

      // Copy assets from the public folderx
      // Reference: https://github.com/kevlened/copy-webpack-plugin
      new CopyWebpackPlugin([{
        from: __dirname + '/src/public'
      }])
    )
  }

  /**
   * Dev server configuration
   * Reference: http://webpack.github.io/docs/configuration.html#devserver
   * Reference: http://webpack.github.io/docs/webpack-dev-server.html
   */
  config.devServer = {
    port: 9500,
    host: 'localhost',
    contentBase: './src/public',
    stats: 'minimal',
    disableHostCheck:true
  };

  return config;
}();