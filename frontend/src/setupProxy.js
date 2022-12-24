const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = (app) => {
    app.use(
        createProxyMiddleware("/auth", {
            target: "http://192.168.45.95:8765",
            changeOrigin: true,
        })
    );
}