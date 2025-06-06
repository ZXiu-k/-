export function isTokenExpired(token) {
  try {
    // 分割 JWT，获取 payload 部分
    const payloadBase64 = token.split('.')[1]
    // 替换 URL 安全字符并解码
    const payloadJson = atob(
      payloadBase64.replace(/-/g, '+').replace(/_/g, '/')
    )
    const payload = JSON.parse(payloadJson)

    // 检查是否有 exp 字段
    if (payload.exp === undefined) return true // 无过期时间视为无效

    // 将秒转换为毫秒（JavaScript Date 使用毫秒）
    const expTime = payload.exp * 1000
    const currentTime = Date.now()

    return currentTime >= expTime
  } catch (error) {
    // console.error('解析 JWT 失败:', error)
    return true // 解析失败视为过期
  }
}

export function isAdmin(token) {
  try {
    // 分割 JWT，获取 payload 部分
    const payloadBase64 = token.split('.')[1]
    // 替换 URL 安全字符并解码
    const payloadJson = atob(
      payloadBase64.replace(/-/g, '+').replace(/_/g, '/')
    )
    const payload = JSON.parse(payloadJson)

    // 检查是否有 role 字段
    if (payload.role === undefined) return false // 无用户角色视为非管理员
    // 检查用户角色是否为管理员
    return payload.role === 1
  } catch (error) {
    // console.error('解析 JWT 失败:', error)
    return false // 解析失败视为非管理员
  }
}
// console.log(isTokenExpired('eyJhbGciOiJIUzI1NiJ9.eyJpZCI6Miwicm9sZSI6MCwiZXhwIjoxNzQ2NDY2NTc3fQ.fQ1rOFxYlm_bVzpDr_McHdYVLC4XD1d4XILFNzFfSCo'))

// console.log(isAdmin('eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwicm9sZSI6MSwiZXhwIjoxNzQ2NTQyMjQyfQ.DulglEfvOOevO9oTnm_WyQ3u7f9zBhaRh7hVsEnHlhg'))