# P2 · Vinilos Sur — RETO 3 (regla de negocio)

**Tiempo objetivo:** 30 minutos. Cronométrate y apunta la hora de inicio.
**Puedes usar:** la guía, las plantillas `dw…`, y como consulta `Tasca-Reto3-Duplicados` o `ProductosJdbc-retos-resueltos`.
**Sin pedirme ayuda** hasta terminar (salvo 10 minutos atascado en el mismo error).

## Qué se pide

La tienda no quiere tener el mismo disco dado de alta dos veces. No puede haber dos discos
con el mismo nombre, se escriba como se escriba en mayúsculas o minúsculas.

Si al dar de alta o al modificar un disco su nombre ya lo tiene **otro** disco, el
formulario vuelve a aparecer con el mensaje *"Ya existe un disco con ese nombre"* junto
al campo Nombre, y con todo lo que se había escrito. Modificar un disco sin cambiarle el
nombre tiene que seguir funcionando.

## Comprobaciones (TODAS a mano, con la app arrancada)

- [ ] Alta "abbey ROAD", precio 20, género Pop → mensaje junto a Nombre; precio y género siguen rellenos.
- [ ] Alta "  Thriller  " (con espacios delante y detrás) → mensaje.
- [ ] Editar *Kind of Blue* cambiando solo el precio → se guarda sin error.
- [ ] Editar *Kind of Blue* y ponerle de nombre "Rumours" → mensaje.
- [ ] Alta "Back in Black" → se crea normal y sale en el listado.

## Extra (solo si te sobra tiempo)

La tienda cambia de idea: el mismo nombre **sí** se permite si es de otro género
(puede haber un "Greatest Hits" en Rock y otro en Pop), pero no dos iguales en el mismo género.

## Al terminar, apunta

- Cuánto has tardado.
- Qué archivos has tocado y en qué orden.
- Dónde te has atascado.
