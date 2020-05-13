const pkg = require('./package.json')

module.exports = {
  assetsDir: 'static',
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:5000',
        changeOrigin: true,
        pathRewrite: {
          '^/api': 'api'
        }
      },
      '/root': {
        target: 'http://localhost:5000',
        changeOrigin: true,
        pathRewrite: {
          '^/root': ''
        }
      }
    }
  }
}
