# Configuración de Git Local para este Repositorio (Windows 11)

Este documento explica cómo configurar una cuenta de Git específica para este repositorio, sin afectar tu configuración global.

## ¿Por qué configuración local?

La configuración local te permite usar diferentes credenciales de Git para diferentes repositorios. Esto es útil cuando:
- Trabajas con cuentas personales y laborales
- Necesitas diferentes emails para diferentes proyectos
- Quieres mantener separadas tus identidades de Git

## Pasos para Configurar Git Local

### 1. Verifica tu configuración actual

Primero, revisa tu configuración global y local actual:

```bash
# Ver configuración global
git config --global --list

# Ver configuración local (específica de este repo)
git config --local --list
```

### 2. Configura tu nombre y email localmente

Ejecuta estos comandos en la raíz de este repositorio:

```bash
# Configurar nombre de usuario local
git config --local user.name "Tu Nombre"

# Configurar email local
git config --local user.email "tu-email@ejemplo.com"
```

**Importante:** Reemplaza `"Tu Nombre"` y `"tu-email@ejemplo.com"` con tus datos reales.

### 3. Verifica que la configuración local esté activa

```bash
# Ver solo la configuración de este repositorio
git config --local --list

# Ver qué configuración está usando Git (local sobrescribe global)
git config user.name
git config user.email
```

### 4. (Opcional) Configurar credenciales específicas

Si necesitas usar credenciales diferentes para este repositorio:

```bash
# Deshabilitar el helper de credenciales global solo para este repo
git config --local --unset credential.helper

# O configurar un helper específico
git config --local credential.helper manager
```

## Verificar que Funciona

Después de configurar, puedes verificar que todo está correcto:

```bash
# Esto mostrará la configuración que Git usará (local tiene prioridad)
git config --list --show-origin | grep user
```

Deberías ver que `user.name` y `user.email` vienen del archivo `.git/config` (local) en lugar de archivos globales.

## Ubicación de las Configuraciones

- **Global:** `C:\Users\TuUsuario\.gitconfig`
- **Local:** `ruta\del\repo\.git\config`

La configuración local se guarda en `.git/config` dentro de este repositorio y **solo** afecta a este repositorio.

## Eliminar Configuración Local

Si necesitas volver a usar la configuración global:

```bash
git config --local --unset user.name
git config --local --unset user.email
```

## Ejemplo Completo

```bash
# Navega al repositorio
cd C:\Coding\cosaM\apartamentosapi

# Configura tu cuenta local
git config --local user.name "Juan Pérez"
git config --local user.email "juan.perez@trabajo.com"

# Verifica
git config user.name
git config user.email

# Haz un commit de prueba para verificar
git commit --allow-empty -m "Test de configuración local"
git log -1 --pretty=format:"%an <%ae>"
```

## Notas Adicionales

- La configuración local **sobrescribe** la configuración global para este repositorio
- No necesitas modificar tu configuración global
- Cada repositorio puede tener su propia configuración local
- Los cambios solo afectan commits futuros, no los anteriores
